package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UtentiController implements Initializable, DataInitializable<Object> {

	@FXML
	private TableView<Cliente> utentiTable;
	@FXML
	private TableColumn<Cliente, String> nomeTableColumn;
	@FXML
	private TableColumn<Cliente, String> cognomeTableColumn;
	@FXML
	private TableColumn<Cliente, String> usernameTableColumn;
	@FXML
	private TableColumn<Cliente, Button> modificaTableColumn;
	@FXML
	private TableColumn<Cliente, Button> eliminaTableColumn;
	@FXML
	private TableColumn<Cliente, String> numeroPatenteTableColumn;
	@FXML
	private TableColumn<Cliente, String> scadenzaPatenteTableColumn;

	private ViewDispatcher dispatcher;
	private static UtenteService utenteService; 

	public UtentiController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();

	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		numeroPatenteTableColumn.setCellValueFactory(new PropertyValueFactory<>("numeroPatente"));
		scadenzaPatenteTableColumn.setCellValueFactory(new PropertyValueFactory<>("scadenzaPatente"));
		modificaTableColumn.setStyle("-fx-aligment: CENTER;");
		modificaTableColumn.setCellValueFactory((CellDataFeatures<Cliente, Button> param) -> {
			final Button modificaButton = new Button("Modifica");
			modificaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modificaUtente", param.getValue());
			});
			return new SimpleObjectProperty<Button>(modificaButton);
		});
		eliminaTableColumn.setStyle("-fx-aligment: CENTER;");
		eliminaTableColumn.setCellValueFactory((CellDataFeatures<Cliente, Button> param) -> {
			final Button eliminaButton = new Button("Elimina");
			eliminaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("eliminaUtente", param.getValue());
			});
			return new SimpleObjectProperty<Button>(eliminaButton);
		});

		try {
			List<Cliente> clienti = utenteService.findAllClienti();
			ObservableList<Cliente> clientiData = FXCollections.observableArrayList(clienti);
			utentiTable.setItems(clientiData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@FXML
	public void aggiungiUtenteAction(ActionEvent event) {
		Cliente cliente = new Cliente();
		dispatcher.renderView("modificaUtente", cliente);

	}

}
