package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.domain.Amministratore;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;
import it.univaq.disim.oop.firefly.view.MenuElement;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class LayoutController implements Initializable, DataInitializable<Utente> {
	private static final MenuElement MENU_HOME = new MenuElement("Home", "home");
	private static final MenuElement[] MENU_AMMINISTRATORE = { new MenuElement("Gestione Operatori", "operatori"),
			new MenuElement("Gestione utenti", "utenti"), new MenuElement("Gestione veicoli", "veicoli"),
			new MenuElement("Tipologie Veicoli", "tipologieVeicoli"), new MenuElement("Feedback", "feedback"),
			new MenuElement("Noleggi in Corso", "noleggiInCorso"),
			new MenuElement("Interventi di assistenza", "assistenzaAmministratore") };

	private static final MenuElement[] MENU_OPERATORE = { new MenuElement("Veicoli", "veicoliOperatore"),
			new MenuElement("Prenotazioni di noleggio", "prenotazioniOperatore"),
			new MenuElement("Interventi di assistenza", "assistenza"), new MenuElement("Feedback", "feedback"),
			new MenuElement("Noleggi in Corso", "noleggiInCorso"), new MenuElement("Riconsegna", "riconsegna") };

	private static final MenuElement[] MENU_CLIENTE = { new MenuElement("Veicoli", "veicoli"),
			new MenuElement("Preventivo", "preventivo"), new MenuElement("Notifiche", "notifiche"),
			new MenuElement("Richiedi assistenza", "assistenza") };

	private static final MenuElement MENU_ACCOUNT = new MenuElement("Account", "account");
	@FXML
	private VBox menuBar;
	@FXML
	private ImageView esci;
	private ViewDispatcher dispatcher;
	private Utente utente; 
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dispatcher = ViewDispatcher.getInstance();

	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
		menuBar.getChildren().add(new Separator());
		menuBar.getChildren().addAll(createButton(MENU_HOME));
		if (utente instanceof Amministratore) {
			if (utente.getId() != null) {
				if (utente.getId() == 1) {
					MenuElement gestioneAmministratori = new MenuElement("Gestione Amministratori",
							"gestioneAmministratori");
					menuBar.getChildren().add(createButton(gestioneAmministratori));
				}
			}
			for (MenuElement menu : MENU_AMMINISTRATORE) {
				menuBar.getChildren().add(createButton(menu));
			}

		}

		if (utente instanceof Operatore) {
			for (MenuElement menu : MENU_OPERATORE) {
				menuBar.getChildren().add(createButton(menu));
			}

		}

		if (utente instanceof Cliente) {
			for (MenuElement menu : MENU_CLIENTE) {
				menuBar.getChildren().add(createButton(menu));
			}

		}
		menuBar.getChildren().add(new Separator());
		menuBar.getChildren().addAll(createButton(MENU_ACCOUNT));
	}

	@FXML
	public void esciAction(MouseEvent event) {

		dispatcher.logout();
	}

	private Button createButton(MenuElement viewItem) {
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;-fx-border-color: #9757a3");
		button.setTextFill(Paint.valueOf("black"));
		button.setPrefHeight(12);
		button.setPrefWidth(240);
		button.setOnAction((ActionEvent event) -> {
			dispatcher.renderView(viewItem.getVista(), utente);
		});
		return button;
	}

}
