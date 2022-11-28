package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Amministratore;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class EliminaUtenteController implements Initializable, DataInitializable<Utente> {
	@FXML
	private Label nomeLabel;
	@FXML
	private Label cognomeLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label ruoloLabel;
	private ViewDispatcher dispatcher;
	private UtenteService utenteService;
	private Utente utente;

	public EliminaUtenteController() {
		dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Utente utente) { 
		this.utente = utente;
		nomeLabel.setText(utente.getNome());
		cognomeLabel.setText(utente.getCognome());
		usernameLabel.setText(utente.getUsername());
		if (utente instanceof Cliente) {
			ruoloLabel.setText("Cliente");
		}if(utente instanceof Operatore) {
			ruoloLabel.setText("Operatore");
		}else
			ruoloLabel.setText("Amministratore");
	}

	@FXML
	public void eliminaAction(ActionEvent e) {
		try {
			if (utente instanceof Operatore) {
				Operatore op = (Operatore) utente;
				utenteService.deleteOperatore(op);
				dispatcher.renderView("operatori", null);
			}
			if (utente instanceof Cliente) {
				Cliente cl = (Cliente) utente;
				utenteService.deleteCliente(cl);
				dispatcher.renderView("utenti", null);
			}
			if (utente instanceof Amministratore) {
				Amministratore ad=(Amministratore)utente;
				utenteService.deleteAmministratore(ad);
				dispatcher.renderView("gestioneAmministratori", null);
				
			}
		} catch (BusinessException ex) {
			dispatcher.renderError(ex);
		}

	}

	@FXML
	public void annullaAction(ActionEvent e) {
		if (utente instanceof Operatore)
			dispatcher.renderView("operatori", null);
		else
			dispatcher.renderView("utenti", null);
	}

}
