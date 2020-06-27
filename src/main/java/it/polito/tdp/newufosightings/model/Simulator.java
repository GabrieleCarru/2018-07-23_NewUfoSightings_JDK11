package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.newufosightings.model.Event.EventType;

public class Simulator {

	// Coda degli eventi
	private PriorityQueue<Event> queue;
	
	// variabili
	private int t1;
	private int alpha;
	private int anno;
	private String shape;
	private Graph<State, DefaultWeightedEdge> graph;
	private Map<String, State> idMap;
	private Map<State, Double> defconMap;
	private Model model;
	private Random random;
	
	// Modello
	
	public Simulator(int t1, int alpha, int anno, String shape, Model model, Graph<State, DefaultWeightedEdge> graph, Map<String, State> idMap) {
		this.t1 = t1;
		this.alpha = alpha;
		this.graph = graph;
		this.model = model;
		this.idMap = new HashMap<>(idMap);
	}
	
	public void init() {
		random = new Random();
		
		queue = new PriorityQueue<Event>();
		
		List<State> stati = new ArrayList<>(this.graph.vertexSet());
		List<Sighting> avvistamenti = this.model.getAvvistamenti(anno, shape);
		defconMap = new HashMap<>();		
		
		// Inizializzo una mappa con tutti gli stati presenti nel grafo e valore di defcon a 5
		for(State s : stati) {
			defconMap.put(s, 5.00);
		}
		
		for(Sighting avv : avvistamenti) {
			queue.add(new Event(idMap.get(avv.getState()), avv.getDatetime(), EventType.AVVISTAMENTO));
		}
	}
	
	public void run() {
		if(!queue.isEmpty()) {
			processEvent(queue.poll());
		}
	}

	private void processEvent(Event e) {
		
		switch(e.getTipo()) {
		
		case AVVISTAMENTO:
			
			if(defconMap.get(e.getStato()) >= 1 && defconMap.get(e.getStato()) <= 5) {
				Double nuovoDef = defconMap.get(e.getStato());
				nuovoDef = nuovoDef - 1;
				defconMap.remove(e.getStato());
				defconMap.put(e.getStato(), nuovoDef);
				
				// se minore di alpha anche i vicini diminuiscono il proprio defcon di 0.5
				if((random.nextDouble() * 100) < alpha) {
					for(State s : Graphs.neighborListOf(this.graph, e.getStato())) {
						if(defconMap.get(s) >= 1 && defconMap.get(s) <= 5) {
							queue.add(new Event(s, e.getData(), EventType.AVVISTAMENTO_VICINI));
						}
					}
				}
				
				queue.add(new Event(e.getStato(), e.getData().plusDays(t1), EventType.CESSATA_ALLERTA));
				
			}
			break;
			
		case AVVISTAMENTO_VICINI:
			
			Double nuovoDef = defconMap.get(e.getStato());
			nuovoDef = nuovoDef - 0.5;
			defconMap.remove(e.getStato());
			defconMap.put(e.getStato(), nuovoDef);
			
			queue.add(new Event(e.getStato(), e.getData().plusDays(t1), EventType.CESSATA_ALLERTA_VICINI));
			
			break;
			
		case CESSATA_ALLERTA:
			
			if(defconMap.get(e.getStato()) < 5) {
				Double nuovoDef1 = defconMap.get(e.getStato());
				nuovoDef1 = nuovoDef1 + 1;
				defconMap.remove(e.getStato());
				defconMap.put(e.getStato(), nuovoDef1);
			}
			
			if(defconMap.get(e.getStato()) > 5) {
				Double setMax = 5.00;
				defconMap.remove(e.getStato());
				defconMap.put(e.getStato(), setMax);
			}
			
			break;
			
		case CESSATA_ALLERTA_VICINI: 
			
			if(defconMap.get(e.getStato()) < 5) {
				Double nuovoDef1 = defconMap.get(e.getStato());
				nuovoDef1 = nuovoDef1 + 0.5;
				defconMap.remove(e.getStato());
				defconMap.put(e.getStato(), nuovoDef1);
			}
			
			if(defconMap.get(e.getStato()) > 5) {
				Double setMax = 5.00;
				defconMap.remove(e.getStato());
				defconMap.put(e.getStato(), setMax);
			}
			
			break;
		
		}
		
	}
	
	public Map<State, Double> getDefconByStates() {
		return this.defconMap;
	}
	
}
