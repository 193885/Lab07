package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutageDAO;

public class Model {

	private PowerOutageDAO podao;
	
	private List <Nerc> nerc;
	
	private List <PowerOutage> pwrOut;
	
	private NercIdMap ncim;
	
	private List<PowerOutage> soluzione;
	
	private int maxClientiColpiti;
	
	private List <PowerOutage> pwOutOrdinatieFiltrati;

	public Model() {
		
		podao = new PowerOutageDAO();
		
		ncim = new NercIdMap();
		
		nerc = podao.getNercList(ncim);
		
		pwrOut = podao.getAllPow();
		
	}
	
	public List<Nerc> getNercList() {
		
		return nerc;
	}

	public List<PowerOutage> trovaOttimo(Nerc n, Integer h, Integer y) {
		
		soluzione = new ArrayList <>();
		
		maxClientiColpiti = 0;
		
		pwOutOrdinatieFiltrati = new ArrayList<>();
		
		for (PowerOutage p : pwrOut) {
			
		
			if ( p.getIdNerc() == n.getId() ) 
				
				pwOutOrdinatieFiltrati.add(p);
			
		}
		
		pwOutOrdinatieFiltrati.sort(new Comparator<PowerOutage>() {
			
		
			@Override
			public int compare(PowerOutage o1, PowerOutage o2) {
				return o1.getDataInizio().compareTo(o2.getDataInizio()); //PER POTER FARE DIFFERENZA TRA DATA EVENTO PIU VECCHIO E ULTIMO INSERITO IN PARZIALE	
				
			}
		});

		recursive(soluzione, y, h);

		return soluzione;
		
	}
	
	private void recursive(List<PowerOutage> parziale, int anniMax, int oreMax) {
		
		if(totClientiColpiti(parziale) > maxClientiColpiti) {
			
			maxClientiColpiti = totClientiColpiti(parziale);
			
			soluzione = new ArrayList<PowerOutage>(parziale);
		}
				
		for (PowerOutage p : pwOutOrdinatieFiltrati) {
			
			if(!parziale.contains(p)) {
							
				parziale.add(p);
				
				if(controlloOre(parziale,oreMax) && controlloAnni (parziale,anniMax))
					
					recursive(soluzione, anniMax, oreMax);
				
				parziale.remove(p);
			}		
		}	
	}
	
	private boolean controlloAnni(List<PowerOutage> parziale, int anniMax) { // condizioni del pto 2
			
		if (parziale.size() >=2 ) { //SE E' IL PRIMO ELEMENTO NON HO ALCUN VINCOLO 
				
		int a1 = parziale.get(0).getAnno(); // evento piu' vecchio
		int a2 = parziale.get(parziale.size() - 1).getAnno(); //evento piu' recente
		
		if (a2 - a1 /*+1*/>= anniMax) 
			return false;
		
		}
		
		return true;
	}

	private boolean controlloOre(List<PowerOutage> parziale, int oreMax) {
		
		int totale = totOrediPwrOut(parziale);
		
		if (totale > oreMax) 
			return false;
		
		return true;
	}

	public List<Integer> getListaAnniPwrOut() {
		
		Set<Integer> yearSet = new HashSet<Integer>();
		
		for (PowerOutage event : pwrOut) {
			
			yearSet.add(event.getAnno());
		}
		
		List<Integer> yearList = new ArrayList<Integer>(yearSet);
		
		return yearList;
	}

	public Integer totClientiColpiti(List<PowerOutage> worstCase) {
		
		int tot=0;
		
		for (PowerOutage pwr : worstCase) {
			
			tot+=pwr.getClientiColpiti();
			
		}

		return tot;
	}

	public Integer totOrediPwrOut(List<PowerOutage> worstCase) {
		
		int tot=0;
		
		for (PowerOutage pwr : worstCase) {
			
			tot+=pwr.getDurata();
			
		}

		return tot;
	}

}
