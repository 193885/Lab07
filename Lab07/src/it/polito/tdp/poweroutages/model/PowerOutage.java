package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {
	
	private int id; 	
	private int clientiColpiti;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	
	private int anno;   //ATTRIBUTI UTILI PER ALGORITMO RICORSIVO
	private long  durata;
	
	private int idNerc; // realzione 1 a n a un black out corrisponde una sola nerc NON E' UN ATTRIBUTO DEL JAVA BEAN IN QUANTO COSTITUISCE UNA CHIAVE ESTERNA
		
	public PowerOutage(int id, int clientiColpiti, LocalDateTime dataInizio, LocalDateTime dataFine, int idNerc) {
		
		this.id = id;
		this.clientiColpiti = clientiColpiti;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.idNerc = idNerc;
		
		durata = dataInizio.until(dataFine, ChronoUnit.HOURS);
		
		anno = dataInizio.getYear();
				
	}
	
	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getClientiColpiti() {
		return clientiColpiti;
	}
	
	public void setClientiColpiti(int clientiColpiti) {
		this.clientiColpiti = clientiColpiti;
	}
	
	public LocalDateTime getDataInizio() {
		return dataInizio;
	}
	
	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	public LocalDateTime getDataFine() {
		return dataFine;
	}
	
	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}
	
	public int getIdNerc() {
		return idNerc;
	}
	
	public void setIdNerc(int idNerc) {
		this.idNerc = idNerc;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return anno +" "+ dataInizio +" "+dataFine+" " + durata+" "+clientiColpiti;
	}

	public long getDurata() {
		return durata;
	}

	public void setDurata(long durata) {
		this.durata = durata;
	}
}