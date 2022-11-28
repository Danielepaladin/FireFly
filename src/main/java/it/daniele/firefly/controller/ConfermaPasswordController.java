package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Amministratore;
import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ConfermaPasswordController implements Initializable, DataInitializable<Utente> {
	@FXML
	private Label label;
	private ViewDispatcher dispatcher;
	private UtenteService utenteService;
	private Utente utente;

	public ConfermaPasswordController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void initializeData(Utente utente) {
		this.utente=utente;
		try {
			if (utente instanceof Operatore) {
				Operatore op = (Operatore) utente; 
				utenteService.createOperatore(op);
				StringBuilder s = new StringBuilder();
				s.append(op.getNome().toUpperCase());
				s.append(op.getId());
				// Rubato da internet
				int leftLimit = 97;
				int rightLimit = 122;
				int targetStringLength = 4;
				Random random = new Random();
				StringBuilder buffer = new StringBuilder(targetStringLength);
				for (int i = 0; i < targetStringLength; i++) {
					int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
					buffer.append((char) randomLimitedInt);
				}
				String generatedString = buffer.toString();
				s.append(generatedString);
				op.setPassword(s.toString());
				utenteService.updateOperatore(op);

				label.setText("La password generata per l'operatore " + op.getNome() + " " + op.getCognome() + "è :"
						+ s.toString());

			}
			if (utente instanceof Amministratore) {
				Amministratore ad = (Amministratore) utente;
				utenteService.createAmministratore(ad);
				StringBuilder s = new StringBuilder();
				s.append(ad.getNome().toUpperCase());
				s.append(ad.getId());
				// Rubato da internet
				int leftLimit = 97;
				int rightLimit = 122;
				int targetStringLength = 4;
				Random random = new Random();
				StringBuilder buffer = new StringBuilder(targetStringLength);
				for (int i = 0; i < targetStringLength; i++) {
					int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
					buffer.append((char) randomLimitedInt);
				}
				String generatedString = buffer.toString();
				s.append(generatedString);
				ad.setPassword(s.toString());
				

				label.setText("La password generata per l'amministratore " + ad.getNome() + " " + ad.getCognome() + "è : "
						+ s.toString());

			}
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void confermaAction(ActionEvent e) {
		if(utente instanceof Operatore) {
		dispatcher.renderView("operatori", null);
		}
		if (utente instanceof Amministratore ) {
			dispatcher.renderView("gestioneAmministratori", null);
		}
	}

}
