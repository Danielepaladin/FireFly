package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class SignedinController implements Initializable, DataInitializable<Object> {
	@FXML
	private Button indietro;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void indietro(ActionEvent e) {
		ViewDispatcher dispatcher = ViewDispatcher.getInstance();
		dispatcher.logout();
	}

}
