package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.PrenotazioneService;
import it.univaq.disim.oop.firefly.domain.PrenotazioneNoleggio;
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

public class PrenotazioniOperatoreController implements Initializable, DataInitializable<Object> {
	@FXML
	private TableView<PrenotazioneNoleggio> prenotazioniTableView;
	@FXML
	private TableColumn<PrenotazioneNoleggio, String> clienteTableColumn;
	@FXML
	private TableColumn<PrenotazioneNoleggio, String> veicoloTableColumn;
	@FXML
	private TableColumn<PrenotazioneNoleggio, String> dataInizioTableColumn;
	@FXML
	private TableColumn<PrenotazioneNoleggio, String> dataFineTableColumn;
	@FXML
	private TableColumn<PrenotazioneNoleggio, Button> notificaTableColumn;
	private ViewDispatcher dispatcher;
	private PrenotazioneService prenotazioneSevice;

	public PrenotazioniOperatoreController() {
		dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		this.prenotazioneSevice = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		clienteTableColumn.setCellValueFactory((CellDataFeatures<PrenotazioneNoleggio, String> param) -> {
			StringBuilder nomeCliente = new StringBuilder();
			nomeCliente.append(param.getValue().getCliente().getNome());
			nomeCliente.append(" ");
			nomeCliente.append(param.getValue().getCliente().getCognome());
			return new SimpleStringProperty(nomeCliente.toString());
		});
		veicoloTableColumn.setCellValueFactory((CellDataFeatures<PrenotazioneNoleggio, String> param) -> {
			StringBuilder nomeVeicolo = new StringBuilder();
			nomeVeicolo.append(param.getValue().getVeicolo().getCasaProduttrice());
			nomeVeicolo.append(" ");
			nomeVeicolo.append(param.getValue().getVeicolo().getNome());
			return new SimpleStringProperty(nomeVeicolo.toString());
		});
		dataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizioNoleggio"));
		dataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFineNoleggio"));

		notificaTableColumn.setStyle("-fx-alignment: CENTER;");
		notificaTableColumn.setCellValueFactory((CellDataFeatures<PrenotazioneNoleggio, Button> param) -> {
			final Button notificaButton = new Button("Invia notifica");
			notificaButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("notifica", param.getValue());
			});
			return new SimpleObjectProperty<Button>(notificaButton);
		});

		try {
			List<PrenotazioneNoleggio> prenotazioni = prenotazioneSevice.findAllPrenotazioni();
			ObservableList<PrenotazioneNoleggio> prenotazioniData = FXCollections.observableArrayList(prenotazioni);
			prenotazioniTableView.setItems(prenotazioniData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
	}
}
