package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SigninController implements Initializable, DataInitializable<Object> {
	@FXML
	private TextField nome;
	@FXML
	private TextField cognome;
	@FXML
	private TextField codiceFiscale;
	@FXML
	private DatePicker dataDiNascita;
	@FXML
	private TextField numeroPatente;
	@FXML
	private DatePicker scadenzaPatente;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button signinButton;
	@FXML
	private ImageView indietro;
	@FXML
	private Label errorLabel;
	private ViewDispatcher dispatcher;
	private UtenteService utenteService;
 
	public SigninController() {
		dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory=FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		signinButton.disableProperty()
				.bind(nome.textProperty().isEmpty().or(cognome.textProperty().isEmpty()
						.or(codiceFiscale.textProperty().isEmpty().or(numeroPatente.textProperty().isEmpty()
								.or(username.textProperty().isEmpty().or(password.textProperty().isEmpty()))))));

	}

	@FXML
	public void signinAction(ActionEvent e) {
		if (scadenzaPatente.getValue().isBefore(LocalDate.now())) {
			errorLabel.setText("Inserire una patente valida");
		} else {

			try {
				Cliente cl = new Cliente();
				cl.setNome(nome.getText());
				cl.setCognome(cognome.getText());
				cl.setCodiceFiscale(codiceFiscale.getText());
				cl.setDataNascita(dataDiNascita.getValue());
				cl.setNumeroPatente(numeroPatente.getText());
				cl.setPassword(password.getText());
				cl.setScadenzaPatente(scadenzaPatente.getValue());
				cl.setUsername(username.getText());

				utenteService.createCliente(cl);

			} catch (Exception ex) {
				dispatcher.renderError(ex);
			}
			dispatcher.signedin();
		}
	}

	@FXML
	public void indietro(MouseEvent e) {
		ViewDispatcher dispatcher = ViewDispatcher.getInstance();
		dispatcher.logout();
	}
}
