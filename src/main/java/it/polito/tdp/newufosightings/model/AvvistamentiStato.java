package it.polito.tdp.newufosightings.model;

public class AvvistamentiStato implements Comparable<AvvistamentiStato>{

	private State stato;
	private Integer pesoVicini;
	/**
	 * @param stato
	 * @param pesoVicini
	 */
	public AvvistamentiStato(State stato, Integer pesoVicini) {
		super();
		this.stato = stato;
		this.pesoVicini = pesoVicini;
	}
	
	public State getStato() {
		return stato;
	}

	public Integer getPesoVicini() {
		return pesoVicini;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvvistamentiStato other = (AvvistamentiStato) obj;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return stato.getId() + " - " + stato.getName() + "   | " + pesoVicini + "\n";
	}

	@Override
	public int compareTo(AvvistamentiStato o) {
		return -(this.getPesoVicini().compareTo(o.getPesoVicini()));
	}
	
	
	
}
