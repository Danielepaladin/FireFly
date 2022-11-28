package it.univaq.disim.oop.firefly.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Notifica {
	private String testo;
	private Cliente cliente;
	private Integer id;
	private LocalDate dataRitiro;
	private LocalDateTime oraRitiro;
	private Veicolo veicolo;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataRitiro() {
		return dataRitiro;
	}

	public void setDataRitiro(LocalDate dataRitiro) {
		this.dataRitiro = dataRitiro;
	}

	public LocalDateTime getOraRitiro() {
		return oraRitiro;
	}

	public void setOraRitiro(LocalDateTime oraRitiro) {
		this.oraRitiro = oraRitiro;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}



}
