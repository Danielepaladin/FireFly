package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;

public class VeicoliOperatoreController implements Initializable, DataInitializable<Object> {
	@FXML
	private TableView<Veicolo> veicoliTableView;
	@FXML
	private TableColumn<Veicolo, String> casaProduttriceTableColumn;
	@FXML
	private TableColumn<Veicolo, String> nomeTableColumn;
	@FXML
	private TableColumn<Veicolo, String> targaTableColumn;
	@FXML
	private TableColumn<Veicolo, String> annoImmatricolazioneTableColumn;
	@FXML
	private TableColumn<Veicolo, String> tipologiaVeicoloTableColumn;
	@FXML
	private TableColumn<Veicolo, Button> dettagliTableColumn;
	@FXML
	private ComboBox<String> statoVeicoloComboBox;
	private ViewDispatcher dispatcher;
	private static VeicoloService veicoloService;

	public VeicoliOperatoreController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		veicoloService = factory.getVeicoloService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statoVeicoloComboBox.getItems().add("Visualizza tutti");

		for(StatoVeicolo sv:StatoVeicolo.values()){
			statoVeicoloComboBox.getItems().add(sv.toString());
		}
		statoVeicoloComboBox.setValue("Visualizza tutti");
		
		nomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
		casaProduttriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("casaProduttrice"));
		targaTableColumn.setCellValueFactory(new PropertyValueFactory<>("targa"));
		annoImmatricolazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("annoImmatricolazione"));
		tipologiaVeicoloTableColumn.setCellValueFactory((CellDataFeatures<Veicolo, String> param) -> {
			return new SimpleStringProperty(param.getValue().getTipologia().getNomeTipologia());
		});
		dettagliTableColumn.setStyle("-fx-alignment: CENTER;");
		dettagliTableColumn.setCellValueFactory((CellDataFeatures<Veicolo, Button> param) -> {
			final Button dettagliButton = new Button("Dettagli");
			dettagliButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("dettagliVeicolo", param.getValue()); 
			}); 
			return new SimpleObjectProperty<Button>(dettagliButton);
		});
		
		try {//Quando l'operatore apre la schermata vede tutti i veicoli, dopo aver selizionato la tipologia cercata essi vengono filtrati
			List<Veicolo>veicoli=veicoloService.findAllVeicoli();
			ObservableList<Veicolo> veicoloData=FXCollections.observableArrayList(veicoli);
			veicoliTableView.setItems(veicoloData);
		}catch(BusinessException e) {
			dispatcher.renderError(e);
		}
		
		
	}

	@FXML
	public void cercaAction(ActionEvent e) {
		try {
			List<Veicolo> temp = new ArrayList<>();
			temp = veicoloService.findAllVeicoli();
			
			List<Veicolo> veicoliDisponibili = new ArrayList<>();
			List<Veicolo> veicoliNoleggio = new ArrayList<>();
			List<Veicolo> veicoliManutenzione = new ArrayList<>();
			for (Veicolo veicolo : temp) {
				if (veicolo.getStato().equals(StatoVeicolo.DISPONIBILE)) {
					veicoliDisponibili.add(veicolo);
				}
				if (veicolo.getStato().equals(StatoVeicolo.MANUTENZIONE)) {
					veicoliManutenzione.add(veicolo);
				}
				if (veicolo.getStato().equals(StatoVeicolo.NOLEGGIO)) {
					veicoliNoleggio.add(veicolo);
				}
			}
			if (statoVeicoloComboBox.getValue().equals(StatoVeicolo.DISPONIBILE.toString())) {
				ObservableList<Veicolo> veicoloData=FXCollections.observableArrayList(veicoliDisponibili);
				veicoliTableView.setItems(veicoloData);

			}
			if (statoVeicoloComboBox.getValue().equals(StatoVeicolo.NOLEGGIO.toString())) {
				ObservableList<Veicolo> veicoloData=FXCollections.observableArrayList(veicoliNoleggio);
				veicoliTableView.setItems(veicoloData);

			}
			if (statoVeicoloComboBox.getValue().equals(StatoVeicolo.MANUTENZIONE.toString())) {
				ObservableList<Veicolo> veicoloData=FXCollections.observableArrayList(veicoliManutenzione);
				veicoliTableView.setItems(veicoloData);

			}
			if(statoVeicoloComboBox.getValue().equals("Visualizza tutti")) {
				ObservableList<Veicolo> veicoloData=FXCollections.observableArrayList(temp);
				veicoliTableView.setItems(veicoloData);
			}
		} catch (BusinessException ex) {
			dispatcher.renderError(ex);
		}

	}
}
