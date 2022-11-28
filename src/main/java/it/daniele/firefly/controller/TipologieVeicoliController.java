package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.CampoDiUtilizzo;
import it.univaq.disim.oop.firefly.domain.TipologiaVeicolo;
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

public class TipologieVeicoliController implements Initializable, DataInitializable<Object> {
	@FXML
	private TableView<TipologiaVeicolo> tipologieTable;
	@FXML
	private TableColumn<TipologiaVeicolo, String> nomeTableColumn;
	@FXML
	private TableColumn<TipologiaVeicolo, String> caratteristicheTableColumn;
	@FXML
	private TableColumn<TipologiaVeicolo, String> campoUtilizzoTableColumn;
	@FXML
	private TableColumn<TipologiaVeicolo, Button> modificaTableColumn;
	@FXML
	private TableColumn<TipologiaVeicolo, Button> eliminaTableColumn;
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloService;

	public TipologieVeicoliController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		veicoloService = factory.getVeicoloService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nomeTipologia"));
		caratteristicheTableColumn.setCellValueFactory((CellDataFeatures<TipologiaVeicolo, String> param) -> {
			StringBuilder s=new StringBuilder();
			for (int i=0;i<param.getValue().getCaratteristiche().size();i++) {
				s.append(param.getValue().getCaratteristiche().get(i));
				s.append("\n");
				
			}
			return new SimpleStringProperty(s.toString());
		});
		campoUtilizzoTableColumn.setCellValueFactory((CellDataFeatures<TipologiaVeicolo, String> param) -> {
			if(param.getValue().getCampoDiUtilizzo().equals( CampoDiUtilizzo.TRASPORTOPERSONE))
			return new SimpleStringProperty("Trasporto Persone");
			else if(param.getValue().getCampoDiUtilizzo().equals( CampoDiUtilizzo.TRASPORTOANIMALI))
				return new SimpleStringProperty("Trasporto Animali");
			else 
				return new SimpleStringProperty("Trasporto Merci");
		});
		modificaTableColumn.setCellValueFactory((CellDataFeatures<TipologiaVeicolo, Button> param) -> {
			final Button modificaButton = new Button("Modifica");
			modificaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("modificaTipologie", param.getValue());
			});
			return new SimpleObjectProperty<Button>(modificaButton);
		});
		eliminaTableColumn.setStyle("-fx-aligment: CENTER;");
		eliminaTableColumn.setCellValueFactory((CellDataFeatures<TipologiaVeicolo, Button> param) -> {
			final Button eliminaButton = new Button("Elimina");
			eliminaButton.setOnAction((ActionEvent event) -> {
				try {
					veicoloService.deleteTipologiaVeicolo(param.getValue());
					dispatcher.renderView("tipologieVeicoli", null);
				}catch(BusinessException e){
					dispatcher.renderError(e);
				}
			});
			return new SimpleObjectProperty<Button>(eliminaButton);
		});
		try {
			List<TipologiaVeicolo> tipologie = veicoloService.findAllTipologie();
			ObservableList<TipologiaVeicolo> tipologiaData = FXCollections.observableArrayList(tipologie);
			tipologieTable.setItems(tipologiaData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}

	@FXML
	public void aggiungiTipologiaAction(ActionEvent e) {
		TipologiaVeicolo tipologia = new TipologiaVeicolo();
		dispatcher.renderView("modificaTipologie", tipologia);
	}
}
