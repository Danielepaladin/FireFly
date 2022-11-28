package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Operatore;
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

public class OperatoriController implements Initializable, DataInitializable<Object> {

	@FXML
	private TableView<Operatore> operatoriTable;
	@FXML
	private TableColumn<Operatore, String> nomeTableColumn;
	@FXML
	private TableColumn<Operatore, String> cognomeTableColumn;
	@FXML
	private TableColumn<Operatore, String> usernameTableColumn;
	@FXML
	private TableColumn<Operatore, Button> modificaTableColumn;
	@FXML
	private TableColumn<Operatore, Button> eliminaTableColumn;

	private ViewDispatcher dispatcher;
	private static UtenteService utenteService; 

	public OperatoriController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();

	} 

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		modificaTableColumn.setStyle("-fx-aligment: CENTER;");
		modificaTableColumn.setCellValueFactory((CellDataFeatures<Operatore, Button> param) -> {
			final Button modificaButton = new Button("Modifica");
			modificaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modificaUtente", param.getValue());
			});
			return new SimpleObjectProperty<Button>(modificaButton);
		});
		eliminaTableColumn.setStyle("-fx-aligment: CENTER;");
		eliminaTableColumn.setCellValueFactory((CellDataFeatures<Operatore, Button> param) -> {
			final Button eliminaButton = new Button("Elimina");
			eliminaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("eliminaUtente", param.getValue());
			});
			return new SimpleObjectProperty<Button>(eliminaButton);
		});

		try {
			List<Operatore> operatori = utenteService.findAllOperatori();
			ObservableList<Operatore> operatoreData = FXCollections.observableArrayList(operatori);
			operatoriTable.setItems(operatoreData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
 
	}

	@FXML
	public void aggiungiOperatoreAction(ActionEvent event) {
		Operatore operatore = new Operatore();
		dispatcher.renderView("modificaUtente", operatore);

	}

}
