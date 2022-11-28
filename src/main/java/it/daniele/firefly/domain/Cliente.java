package it.univaq.disim.oop.firefly.domain;

import java.time.LocalDate;

public class Cliente extends Utente {
	private String codiceFiscale;
	private String numeroPatente;
	private LocalDate dataNascita;
	private LocalDate scadenzaPatente;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNumeroPatente() {//
		return numeroPatente;
	}

	public void setNumeroPatente(String numeroPatente) {
		this.numeroPatente = numeroPatente;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public LocalDate getScadenzaPatente() {
		return scadenzaPatente;
	}

	public void setScadenzaPatente(LocalDate scadenzaPatente) {
		this.scadenzaPatente = scadenzaPatente;
	}

}
