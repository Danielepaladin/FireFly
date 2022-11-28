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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

//È un casino, forse avrei dovuto separare Operatore da Cliente;
public class ModificaUtenteController implements Initializable, DataInitializable<Utente> {
	@FXML
	private Label titoloLabel;
	@FXML
	private TextField nomeTextField;
	@FXML
	private TextField cognomeTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private Button salvaButton;
	@FXML
	private Button annullaButton;
	@FXML
	private GridPane griglia;
	@FXML
	private TextField numeroPatenteTF;
	@FXML
	private DatePicker scadenzaPatenteDP;
	@FXML
	private TextField codiceFiscaleTextField;
	@FXML
	private DatePicker dataNascitaDatePicker;
	@FXML
	private Label errorLabel;

	private Utente utente;
	private ViewDispatcher dispatcher;
	private UtenteService utenteService;

	public ModificaUtenteController() {
		dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		salvaButton = new Button();
		salvaButton.setText("Salva");
		salvaButton.setPrefWidth(216);
		salvaButton.setOnAction((ActionEvent e) -> {
			salvaAction(e);
		});
		annullaButton = new Button();
		annullaButton.setText("Annulla");
		annullaButton.setPrefWidth(216);
		annullaButton.setOnAction((ActionEvent e) -> {
			annullaAction(e);
		});
		salvaButton.disableProperty().bind(nomeTextField.textProperty().isEmpty()
				.or(cognomeTextField.textProperty().isEmpty().or(usernameTextField.textProperty().isEmpty())));

	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
		if (utente instanceof Operatore) {

			titoloLabel.setText("Aggiungi Operatore:");
			this.utente = utente;

			griglia.add(salvaButton, 0, 6);
			griglia.add(annullaButton, 1, 6);
			if (utente.getId() != null) {
				titoloLabel.setText("Modifica Operatore:");
				this.nomeTextField.setText(utente.getNome());
				this.cognomeTextField.setText(utente.getCognome());
				this.usernameTextField.setText(utente.getUsername());
			}

		}
		if (utente instanceof Amministratore) {

			titoloLabel.setText("Aggiungi Amministratore:");
			this.utente = utente;

			griglia.add(salvaButton, 0, 6);
			griglia.add(annullaButton, 1, 6);
		}

		if (utente instanceof Cliente) {// Forse avrei dovuto fare due cose separatate!
			numeroPatenteTF = new TextField();
			scadenzaPatenteDP = new DatePicker();
			codiceFiscaleTextField = new TextField();
			dataNascitaDatePicker = new DatePicker();
			scadenzaPatenteDP.setEditable(false);
			dataNascitaDatePicker.setEditable(false);// Per non dover controllare se quello che c'è scritto è davvero
														// una data
			salvaButton.disableProperty()
					.bind(numeroPatenteTF.textProperty().isEmpty().or(codiceFiscaleTextField.textProperty().isEmpty()));
			Cliente cliente = (Cliente) utente;

			titoloLabel.setText("Aggiungi Cliente:");
			Label numeroPatente = new Label();
			numeroPatente.setFont(new Font("System", 18));
			numeroPatente.setText("Numero patente:");

			griglia.add(numeroPatente, 0, 6);
			griglia.add(numeroPatenteTF, 1, 6);
			griglia.add(new Separator(), 0, 7);
			griglia.add(new Separator(), 1, 7);
			Label scadenzaPatente = new Label();
			scadenzaPatente.setFont(new Font("System", 18));
			scadenzaPatente.setText("Scadenza Patente:");
			griglia.add(scadenzaPatente, 0, 8);
			scadenzaPatenteDP.setPrefWidth(281);
			griglia.add(scadenzaPatenteDP, 1, 8);
			griglia.add(new Separator(), 0, 9);
			griglia.add(new Separator(), 1, 9);
			Label codiceFiscale = new Label();
			codiceFiscale.setFont(new Font("System", 18));
			codiceFiscale.setText("Codice Fiscale:");

			griglia.add(codiceFiscale, 0, 10);
			griglia.add(codiceFiscaleTextField, 1, 10);
			griglia.add(new Separator(), 0, 11);
			griglia.add(new Separator(), 1, 11);

			Label dataNascita = new Label();
			dataNascita.setFont(new Font("System", 18));
			dataNascita.setText("Data di Nascita:");
			griglia.add(dataNascita, 0, 12);
			griglia.add(dataNascitaDatePicker, 1, 12);
			griglia.add(new Separator(), 0, 13);
			griglia.add(new Separator(), 1, 13);
			griglia.add(salvaButton, 0, 14);
			griglia.add(annullaButton, 1, 14);

			if (utente.getId() != null) {
				titoloLabel.setText("Modifica Cliente");
				this.nomeTextField.setText(utente.getNome());
				this.cognomeTextField.setText(utente.getCognome());
				this.usernameTextField.setText(utente.getUsername());
				numeroPatenteTF.setText(cliente.getNumeroPatente());
				scadenzaPatenteDP.setValue(cliente.getScadenzaPatente());
				codiceFiscaleTextField.setText(cliente.getCodiceFiscale());
				dataNascitaDatePicker.setValue(cliente.getDataNascita());

			}
		}
	}

	public void annullaAction(ActionEvent e) {
		if (utente instanceof Operatore)
			dispatcher.renderView("operatori", null);
		else
			dispatcher.renderView("utenti", null);

	}

	@FXML
	public void salvaAction(ActionEvent e) {
		try {
			utente.setNome(nomeTextField.getText());
			utente.setCognome(cognomeTextField.getText());
			utente.setUsername(usernameTextField.getText());
			if (utente instanceof Amministratore) {
				if (utente.getId() == null) {

					Amministratore ad = (Amministratore) utente;

					dispatcher.renderView("confermaPassword", ad);
				}
			}
			if (utente instanceof Operatore) {
				if (utente.getId() == null) {
					Operatore op = (Operatore) utente;
					dispatcher.renderView("confermaPassword", op);
				} else {
					Operatore op = (Operatore) utente;
					utenteService.updateOperatore(op);
					dispatcher.renderView("operatori", null);

				}

			}
			if (utente instanceof Cliente) {
				Cliente cl = (Cliente) utente;
				if (scadenzaPatenteDP.getValue() == null || dataNascitaDatePicker.getValue() == null)
					errorLabel.setText("Attenzione tutti i campi sono obbligatori");
				else {

					cl.setCodiceFiscale(codiceFiscaleTextField.getText());
					cl.setNumeroPatente(numeroPatenteTF.getText());
					cl.setScadenzaPatente(scadenzaPatenteDP.getValue());
					cl.setDataNascita(dataNascitaDatePicker.getValue());
					cl.setPassword(cl.getCodiceFiscale());
					if (utente.getId() == null) {
						utenteService.createCliente(cl);
					} else {
						utenteService.updateCliente(cl);

					}
					dispatcher.renderView("utenti", null);
				}
			}
		} catch (BusinessException exc) {
			dispatcher.renderError(exc);
		}

	}

}
