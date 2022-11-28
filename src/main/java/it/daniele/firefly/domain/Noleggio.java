package it.univaq.disim.oop.firefly.domain;

import java.time.LocalDate;

public class Noleggio {
	private Veicolo veicolo;
	private Integer id;

	private Cliente utente;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private Boolean inCorso;
	public Boolean getInCorso() {
		return inCorso;
	}

	public void setInCorso(Boolean inCorso) {
		this.inCorso = inCorso;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

	public Cliente getUtente() {
		return utente;
	}

	public void setUtente(Cliente utente) {
		this.utente = utente;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDate dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	
}
