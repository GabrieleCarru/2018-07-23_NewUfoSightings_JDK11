package it.polito.tdp.newufosightings.model;

public class Adiacenza {

	private State s1;
	private State s2;
	private Integer peso;
	/**
	 * @param s1
	 * @param s2
	 * @param peso
	 */
	public Adiacenza(State s1, State s2, Integer peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}
	public State getS1() {
		return s1;
	}
	public State getS2() {
		return s2;
	}
	public Integer getPeso() {
		return peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s1 == null) ? 0 : s1.hashCode());
		result = prime * result + ((s2 == null) ? 0 : s2.hashCode());
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
		Adiacenza other = (Adiacenza) obj;
		if (s1 == null) {
			if (other.s1 != null)
				return false;
		} else if (!s1.equals(other.s1))
			return false;
		if (s2 == null) {
			if (other.s2 != null)
				return false;
		} else if (!s2.equals(other.s2))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Adiacenza [s1=" + s1 + ", s2=" + s2 + ", peso=" + peso + "]";
	}
	
	
	
}
