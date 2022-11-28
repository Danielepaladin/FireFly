package it.univaq.disim.oop.firefly.domain;

import java.util.List;

public class TipologiaVeicolo {// Momentaneamente
	private String nomeTipologia;
	private Integer id;
	private List<String> caratteristiche;
	private CampoDiUtilizzo campoDiUtilizzo;

	public Integer getId() {
		return id;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}

	

	public CampoDiUtilizzo getCampoDiUtilizzo() {
		return campoDiUtilizzo;
	}

	public void setCampoDiUtilizzo(CampoDiUtilizzo campoDiUtilizzo) {
		this.campoDiUtilizzo = campoDiUtilizzo;
	}

	public String getNomeTipologia() {
		return nomeTipologia;
	}

	public void setNomeTipologia(String nomeTipologia) {
		this.nomeTipologia = nomeTipologia;
	}

	public List<String> getCaratteristiche() {
		return caratteristiche;
	}

	public void setCaratteristiche(List<String> caratteristiche) {
		this.caratteristiche = caratteristiche;
	}

}
 