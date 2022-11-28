package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomeController implements Initializable, DataInitializable<Utente> {
	@FXML
	private Label benvenuto;
	@FXML
	private VBox vbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Utente utente) {
		StringBuilder testoBenvenuto = new StringBuilder();
		testoBenvenuto.append("Benvenuto ");
		testoBenvenuto.append(utente.getNome());
		testoBenvenuto.append(" ");
		testoBenvenuto.append(utente.getCognome());
		benvenuto.setText(testoBenvenuto.toString());
		if(utente instanceof Operatore) {
			if (utente.getPassword().startsWith((utente.getNome().toUpperCase())+utente.getId())) {
				Label label=new Label();
				label.setFont(new Font("System",22));;
				label.setText("Attenzione! si consiglia di modificare la password");
				vbox.getChildren().add(label);
			}
			
		}

	}
}
