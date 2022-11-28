package it.univaq.disim.oop.firefly.domain;

public class Pagamento {
	private String carta;
	private String cvv;
	private Operatore gestore;
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Operatore getGestore() {
		return gestore;
	}

	public void setGestore(Operatore gestore) {
		this.gestore = gestore;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
