package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteNotFoundException;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.impl.ram.RAMUtenteService;
import it.univaq.disim.oop.firefly.domain.Utente;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable, DataInitializable<Object> {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginButton;
	@FXML
	private Button signinButton;
	@FXML
	private Label errorLabel;

	private ViewDispatcher dispatcher;
	private UtenteService utenteService;

	public LoginController() {
		dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory=FireFlyBusinessFactory.getInstance();
		utenteService=factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.disableProperty().bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty()));
	}
 
	@FXML
	private void loginAction(ActionEvent event) {
		try {
			Utente utente = utenteService.authenticate(username.getText(), password.getText());
			dispatcher.loggedIn(utente);
		} catch (UtenteNotFoundException e) {
			errorLabel.setText("Username o password errati!");
		} catch (BusinessException e) {
			// dispatcher.renderError(e);
		}
	}

	@FXML
	public void signInAction(ActionEvent e) {
		ViewDispatcher dispatcher = ViewDispatcher.getInstance();
		dispatcher.signin();
	}

}
