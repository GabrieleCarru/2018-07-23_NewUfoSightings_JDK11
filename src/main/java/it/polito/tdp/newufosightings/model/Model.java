package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {

	private NewUfoSightingsDAO dao;
	private Graph<State, DefaultWeightedEdge> graph;
	private Map<String, State> idMap;
	private Simulator sim;
	private int anno;
	private String shape;
	
	public Model() {
		dao = new NewUfoSightingsDAO();
	}
	
	public void creaGrafo(int anno, String shape) {
		
		graph = new SimpleWeightedGraph<State, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		List<State> stati = dao.loadAllStates(idMap);
		List<Adiacenza> adiacenze = dao.getAdiacenze(anno, shape, idMap);
		
		// Aggiungo i vertici
		Graphs.addAllVertices(this.graph, stati);
		
		// Aggiungo gli archi
		for(Adiacenza a : adiacenze) {
			if(graph.vertexSet().contains(a.getS1()) && graph.vertexSet().contains(a.getS2())) {
				Graphs.addEdge(this.graph, a.getS1(), a.getS2(), a.getPeso());
			}
		}
		
		this.anno = anno;
		this.shape = shape;
	}
	
	public List<AvvistamentiStato> avvistamentiPerStato() {
		
		List<AvvistamentiStato> result = new ArrayList<>();
		
		for(State s : this.graph.vertexSet()) {
			int pesoVicini = 0;
			List<State> vicini = new ArrayList<>(Graphs.neighborListOf(this.graph, s));
			
			for(State vicino : vicini) {
				pesoVicini += graph.getEdgeWeight(graph.getEdge(s, vicino));
			}
			result.add(new AvvistamentiStato(s, pesoVicini));
		}
		Collections.sort(result);
		return result;
	}
	
	public void Simula(int t1, int alpha) {
		sim = new Simulator(t1, alpha, anno, shape, this, graph, idMap);
		sim.init();
		sim.run();
	}
	
	public Map<State, Double> getDefconByStates() {
		return this.sim.getDefconByStates();
	}
	
	public int getNumberVertex() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumberEdge() {
		return this.graph.edgeSet().size();
	}
	
	public List<String> getAllShapeByAnno(int anno) {
		return dao.getAllShapeByAnno(anno);
	}

	public List<Sighting> getAvvistamenti(int anno, String shape) {
		return dao.loadAllSightings(anno, shape);
	}
	
}
