package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
	Graph<Airport, DefaultWeightedEdge> grafo;
	List<Airport> best;
	int max=0;
	double tot=0;
	
	public Graph<Airport, DefaultWeightedEdge> creaGrafo(double dist) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.loadAllAirports());
		for(Adiacenza a : dao.archi(dist)) {
			if(!grafo.edgeSet().contains(grafo.getEdge(a.getA1(), a.getA2())))
				Graphs.addEdge(grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		System.out.println(grafo);
		return grafo;
	}
	
	public List<Vicino> trovaVicini(Airport a) {
		List<Vicino> vicini = new LinkedList<>();
		for(Airport air : Graphs.neighborListOf(grafo, a)) {
			vicini.add(new Vicino(air, grafo.getEdgeWeight(grafo.getEdge(a, air))));
		}
		Collections.sort(vicini);
		return vicini;
	}
	
	public List<Airport> trovaPercorso (Airport a, double distanza) {
		List<Airport> parziale = new ArrayList<>();
		parziale.add(a);
		cerca(parziale, distanza, 0);
		return best;
	}

	private void cerca(List<Airport> parziale, double distanza, double peso) {
		if(peso>distanza) {
			if(parziale.size()-1>max) {
				max=parziale.size()-1;
				best = new ArrayList<>(parziale);
				best.remove(best.size()-1);
				tot = peso-grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), parziale.get(parziale.size()-2)));
			}
			return;
		}
		if(peso==distanza) {
			if(parziale.size()>max) {
				max = parziale.size();
				best = new ArrayList<>(parziale);
				tot = peso;
			}
			return;
		}
		for(Airport a : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
				if(!parziale.contains(a)) {
					parziale.add(a);
					cerca(parziale, distanza, peso+grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-2), a)));
					parziale.remove(parziale.size()-1);
				}
		}
	}
	
	public double tot() {
		return tot;
	}
	
	
}
