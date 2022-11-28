package it.univaq.disim.oop.firefly.domain;

import java.util.HashSet;
import java.util.Set;

public class Veicolo {
	private Integer id;//
	private String targa;//
	private String nome;//
	private String casaProduttrice;//
	private Integer annoImmatricolazione;//
	private String descrizione;//
	private Set<Feedback> feedbackRilasciati = new HashSet<>();
	private Alimentazione alimentazione;//
	private Integer chilometraggio;//
	private Double tariffaBreveTermine;//
	private Double tariffaMedioTermine;//
	private Double tariffaLungoTermine;//
	private StatoVeicolo stato;//
	private TipologiaVeicolo tipologia;
	
	

	

	public void setFeedbackRilasciati(Set<Feedback> feedbackRilasciati) {
		this.feedbackRilasciati = feedbackRilasciati;
	}

	public TipologiaVeicolo getTipologia() {
		return tipologia;
	}

	public void setTipologia(TipologiaVeicolo tipologia) {
		this.tipologia = tipologia;
	}

	public StatoVeicolo getStato() {
		return stato;
	}

	public void setStato(StatoVeicolo stato) {
		this.stato = stato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCasaProduttrice() {
		return casaProduttrice;
	}

	public void setCasaProduttrice(String casaProduttrice) {
		this.casaProduttrice = casaProduttrice;
	}

	public Integer getAnnoImmatricolazione() {
		return annoImmatricolazione;
	}

	public void setAnnoImmatricolazione(Integer annoImmatricolazione) {
		this.annoImmatricolazione = annoImmatricolazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Feedback> getFeedbackRilasciati() {
		return feedbackRilasciati;
	}

	public void setFeedbackrilasciati(Set<Feedback> feedbackRilasciati) {
		this.feedbackRilasciati = feedbackRilasciati;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alimentazione getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(Alimentazione alimentazione) {
		this.alimentazione = alimentazione;
	}

	public Integer getChilometraggio() {
		return chilometraggio;
	}

	public void setChilometraggio(Integer chilometraggio) {
		this.chilometraggio = chilometraggio;
	}

	public Double getTariffaBreveTermine() {
		return tariffaBreveTermine;
	}

	public void setTariffaBreveTermine(Double tariffaBreveTermine) {
		this.tariffaBreveTermine = tariffaBreveTermine;
	}

	public Double getTariffaMedioTermine() {
		return tariffaMedioTermine;
	}

	public void setTariffaMedioTermine(Double tariffaMedioTermine) {
		this.tariffaMedioTermine = tariffaMedioTermine;
	}

	public Double getTariffaLungoTermine() {
		return tariffaLungoTermine;
	}

	public void setTariffaLungoTermine(Double tariffaLungoTermine) {
		this.tariffaLungoTermine = tariffaLungoTermine;
	}

	
	}


