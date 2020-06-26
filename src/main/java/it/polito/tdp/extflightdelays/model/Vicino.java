package it.polito.tdp.extflightdelays.model;

public class Vicino implements Comparable <Vicino>{
	
	private Airport a;
	private double peso;
	
	public Vicino(Airport a, double peso) {
		super();
		this.a = a;
		this.peso = peso;
	}

	public Airport getA() {
		return a;
	}

	public double getPeso() {
		return peso;
	}

	@Override
	public int compareTo(Vicino o) {
		int x = 0;
		if(this.getPeso()<o.getPeso())
			x = 1;
		if(this.getPeso()>o.getPeso())
			x = -1;
		return x;
	}

	@Override
	public String toString() {
		return a.getAirportName() + " --> " + peso;
	}
	
	

}
