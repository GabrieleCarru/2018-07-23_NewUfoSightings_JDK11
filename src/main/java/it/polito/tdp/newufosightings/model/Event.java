package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{

	public enum EventType {
		AVVISTAMENTO, AVVISTAMENTO_VICINI, CESSATA_ALLERTA, CESSATA_ALLERTA_VICINI;
	}
	
	private State stato;
	private LocalDateTime data;
	private EventType tipo;
	
	/**
	 * @param stato
	 * @param localDateTime
	 * @param tipo
	 */
	public Event(State stato, LocalDateTime localDateTime, EventType tipo) {
		super();
		this.stato = stato;
		this.data = localDateTime;
		this.tipo = tipo;
	}

	public State getStato() {
		return stato;
	}

	public void setStato(State stato) {
		this.stato = stato;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compareTo(Event o) {
		return this.data.compareTo(o.getData());
	}
	
	
	
}
