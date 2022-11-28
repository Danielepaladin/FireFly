package it.univaq.disim.oop.firefly.domain;

import java.time.LocalDate;

public class InterventoAssistenza {
	private LocalDate dataRichiesta;//
	private LocalDate dataCompletamento;//
	private Boolean veicoloSostitutivo;//
	private Boolean completato;//
	private Veicolo veicolo;//
	private Noleggio noleggio;//
	private String descrizione;
	private TipoRichiesta tipoRichesta;
	private Cliente cliente;//
	private Integer id;//

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(LocalDate dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public LocalDate getDataCompletamento() {
		return dataCompletamento;
	}

	public void setDataCompletamento(LocalDate dataCompletamento) {
		this.dataCompletamento = dataCompletamento;
	}

	public Boolean getVeicoloSostitutivo() {
		return veicoloSostitutivo;
	}

	public void setVeicoloSostitutivo(Boolean veicoloSostitutivo) {
		this.veicoloSostitutivo = veicoloSostitutivo;
	}

	public Boolean getCompletato() {
		return completato;
	}

	public void setCompletato(Boolean completato) {
		this.completato = completato;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

	public Noleggio getNoleggio() {
		return noleggio;
	}

	public void setNoleggio(Noleggio noleggio) {
		this.noleggio = noleggio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public TipoRichiesta getTipoRichesta() {
		return tipoRichesta;
	}

	public void setTipoRichesta(TipoRichiesta tipoRichesta) {
		this.tipoRichesta = tipoRichesta;
	}
}
