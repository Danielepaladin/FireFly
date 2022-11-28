package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Amministratore;
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

public class GestioneAmministratoriController implements Initializable, DataInitializable<Object> {

	@FXML
	private TableView<Amministratore> amministratoriTable;
	@FXML
	private TableColumn<Amministratore, String> nomeTableColumn;
	@FXML
	private TableColumn<Amministratore, String> cognomeTableColumn;
	@FXML
	private TableColumn<Amministratore, String> usernameTableColumn;
	@FXML
	private TableColumn<Amministratore, Button> eliminaTableColumn;

	private ViewDispatcher dispatcher;
	private static UtenteService utenteService;

	 public GestioneAmministratoriController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();

	} 

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		cognomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		eliminaTableColumn.setStyle("-fx-aligment: CENTER;");
		eliminaTableColumn.setCellValueFactory((CellDataFeatures<Amministratore, Button> param) -> {
			final Button eliminaButton = new Button("Elimina");
			if(param.getValue().getId()==1) {
				eliminaButton.disableProperty();
			}
			eliminaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("eliminaUtente", param.getValue());
			});
			return new SimpleObjectProperty<Button>(eliminaButton);
		});

		try {
			List<Amministratore> amministratori = utenteService.findAllAmministratori();
			
			ObservableList<Amministratore> amministratoreData = FXCollections.observableArrayList(amministratori);
			amministratoriTable.setItems(amministratoreData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@FXML
	public void aggiungiOperatoreAction(ActionEvent event) {
		Amministratore amministratore = new Amministratore();
		dispatcher.renderView("modificaUtente", amministratore);

	}

}
