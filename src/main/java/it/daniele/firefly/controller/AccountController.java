package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.domain.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AccountController implements Initializable, DataInitializable<Utente> {
	@FXML
	private TextField usernameCorrenteTextField;
	@FXML
	private TextField nuovoUsernameTextField;
	@FXML
	private PasswordField passwordCorrentePasswordField;
	@FXML
	private PasswordField nuovaPasswordPasswordFied;// un pò ripetitivo sto nome
	@FXML
	private Label errorLabel;
	@FXML
	private Label confermaLabel;
	private Utente utente;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
	}
 
	@FXML 
	public void confermaAction(ActionEvent e) {
		if (usernameCorrenteTextField.getText().equalsIgnoreCase(utente.getUsername())
				&& passwordCorrentePasswordField.getText().equals(utente.getPassword())) {
			if (!nuovoUsernameTextField.getText().isEmpty() && !nuovoUsernameTextField.getText().contains(",")) {
				utente.setUsername(nuovoUsernameTextField.getText());
				errorLabel.setText("Username modificato correttamente");

			}

			if (nuovoUsernameTextField.getText().contains(",")) {
				errorLabel.setText("nessuno dei campi può contenere il carattere \",\"");
			}
			if (!nuovaPasswordPasswordFied.getText().isEmpty() && !nuovaPasswordPasswordFied.getText().contains(",")) {
				utente.setPassword(nuovaPasswordPasswordFied.getText());
				confermaLabel.setText("Password modificata correttamente");
			}
			if (nuovaPasswordPasswordFied.getText().contains(",")) {
				errorLabel.setText("nessuno dei campi può contenere il carattere \",\"");
			}
		} else
			errorLabel.setText("Username o password errati");

	}

}
