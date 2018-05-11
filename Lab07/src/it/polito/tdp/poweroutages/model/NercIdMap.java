package it.polito.tdp.poweroutages.model;

import java.util.HashMap;
import java.util.Map;

public class NercIdMap { //wrapper alla mappa al fine di evitare duplicati

	private Map<Integer,Nerc> map;

	public NercIdMap() {
		map = new HashMap<>();
	}
	
	public Nerc get(Nerc n) {
		
		Nerc old = map.get(n.getId());
		
		if(old == null) {
			
			map.put( n.getId(), n);
			
			return n;
		}
		
		return old;
	}
	
	public void put(Integer id, Nerc n) {
		
		map.put(id, n);
	}
	
	public Nerc get(Integer codice) {
	
		return map.get(codice);
	}
	
}
