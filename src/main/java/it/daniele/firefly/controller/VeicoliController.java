package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Alimentazione;
import it.univaq.disim.oop.firefly.domain.StatoVeicolo;
import it.univaq.disim.oop.firefly.domain.Veicolo;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class VeicoliController implements Initializable, DataInitializable<Object> {
	@FXML
	private TableView<Veicolo> veicoliTable;
	@FXML
	private TableColumn<Veicolo, String> nomeTableColumn;
	@FXML
	private TableColumn<Veicolo, String> casaProduttriceTableColumn;
	@FXML
	private TableColumn<Veicolo, String> targaTableColumn;
	@FXML
	private TableColumn<Veicolo, Integer> annoImmatricolazioneTableColumn;
	@FXML
	private TableColumn<Veicolo, Alimentazione> alimentazioneTableColumn;
	@FXML
	private TableColumn<Veicolo, Integer> chilometraggioTableColumn;
	@FXML
	private TableColumn<Veicolo, StatoVeicolo> statoTableColumn;
	@FXML
	private TableColumn<Veicolo, Double> tariffaBreveTermineTableColumn;
	@FXML
	private TableColumn<Veicolo, Double> tariffaMediotermineTableColumn;
	@FXML
	private TableColumn<Veicolo, Double> tariffaLungoTermineTableColumn;
	@FXML
	private TableColumn<Veicolo, String> tipologiaVeicoloTableColumn;
	@FXML
	private TableColumn<Veicolo, Button> azioniTableColumn;

	private ViewDispatcher dispatcher;
	private static VeicoloService veicoloService;

	public VeicoliController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		veicoloService = factory.getVeicoloService();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		casaProduttriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("casaProduttrice"));
		targaTableColumn.setCellValueFactory(new PropertyValueFactory<>("targa"));
		annoImmatricolazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("annoImmatricolazione"));
		alimentazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("alimentazione"));
		chilometraggioTableColumn.setCellValueFactory(new PropertyValueFactory<>("chilometraggio"));
		statoTableColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));
		tariffaBreveTermineTableColumn.setCellValueFactory(new PropertyValueFactory<>("tariffaBreveTermine"));
		tariffaMediotermineTableColumn.setCellValueFactory(new PropertyValueFactory<>("tariffaMedioTermine"));
		tariffaLungoTermineTableColumn.setCellValueFactory(new PropertyValueFactory<>("tariffaLungoTermine"));
		tipologiaVeicoloTableColumn.setCellValueFactory((CellDataFeatures<Veicolo, String> param) -> {
			return new SimpleStringProperty(param.getValue().getTipologia().getNomeTipologia());
		});
		azioniTableColumn.setStyle("-fx-alignment: CENTER;");
		azioniTableColumn.setCellValueFactory((CellDataFeatures<Veicolo, Button> param) -> {
			final Button modificaButton = new Button("Modifica");
			modificaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modificaVeicolo", param.getValue()); 
			});
			return new SimpleObjectProperty<Button>(modificaButton); 
		});
		try {
			List<Veicolo> veicoli=veicoloService.findAllVeicoli();
			ObservableList<Veicolo> veicoloData=FXCollections.observableArrayList(veicoli);
			veicoliTable.setItems(veicoloData);
		}catch(BusinessException e) { 
			dispatcher.renderError(e);
		}
	
	
	
	
	}
@FXML
public void aggiungiVeicoloAction(ActionEvent e) {
	Veicolo veicolo=new Veicolo();
	dispatcher.renderView("modificaVeicolo",veicolo);
}
}
