package it.univaq.disim.oop.firefly.domain;

public class Feedback {
	private Cliente rilasciatoDa;
	private Veicolo veicolo;
	private String testo;
	private Valutazione valutazione;
	private Integer id;

	public Integer getId() { 
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getRilasciatoDa() {
		return rilasciatoDa;
	}

	public void setRilasciatoDa(Cliente rilasciatoDa) {
		this.rilasciatoDa = rilasciatoDa;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

	public String getTesto() {
		return testo;
	}

	public Valutazione getValutazione() {
		return valutazione;
	}

	public void setValutazione(Valutazione valutazione) {
		this.valutazione = valutazione;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}
}
