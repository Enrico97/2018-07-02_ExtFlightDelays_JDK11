package it.polito.tdp.extflightdelays.model;

public class Adiacenza {
	
	private Airport a1;
	private Airport a2;
	private double peso;
	
	public Adiacenza(Airport a1, Airport a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public Airport getA1() {
		return a1;
	}

	public Airport getA2() {
		return a2;
	}

	public double getPeso() {
		return peso;
	}
	
	

}
