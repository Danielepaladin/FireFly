package it.univaq.disim.oop.firefly.domain;

import java.time.LocalDate;

public class PrenotazioneNoleggio {
	private LocalDate dataInizioNoleggio;
	private LocalDate dataFineNoleggio;
	private Veicolo veicolo;
	private Cliente cliente;
	private Integer id;

	public Integer getId() {
		return id;
	} 

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataInizioNoleggio() {
		return dataInizioNoleggio;
	}

	public void setDataInizioNoleggio(LocalDate dataInizioNoleggio) {
		this.dataInizioNoleggio = dataInizioNoleggio;
	}

	public LocalDate getDataFineNoleggio() {
		return dataFineNoleggio;
	}

	public void setDataFineNoleggio(LocalDate dataFineNoleggio) {
		this.dataFineNoleggio = dataFineNoleggio;
	}


	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
