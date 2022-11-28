package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.NoleggioService;
import it.univaq.disim.oop.firefly.domain.Noleggio;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class NoleggiInCorsoController implements Initializable, DataInitializable<Object> {
	@FXML
	private TableView<Noleggio> noleggiInCorsoTable;
	@FXML
	private TableColumn<Noleggio,String> clienteTableColumn;
	@FXML
	private TableColumn<Noleggio,String> veicoloTableColumn;
	@FXML
	private TableColumn<Noleggio, LocalDate> dataInizioTableColumn;
	@FXML
	private TableColumn<Noleggio, LocalDate> dataFineTableColumn;
	@FXML
	private TableColumn<Noleggio,String> idTableColumn;
	
	private ViewDispatcher dispatcher;
	private static NoleggioService noleggioService;
	public NoleggiInCorsoController() {
		this.dispatcher=ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory=FireFlyBusinessFactory.getInstance();
		noleggioService=factory.getNoleggioService();
	}
 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clienteTableColumn.setCellValueFactory((CellDataFeatures<Noleggio, String> param) -> {
			StringBuilder s=new StringBuilder();
			s.append(param.getValue().getUtente().getNome());
			s.append(" ");
			s.append(param.getValue().getUtente().getCognome());
			return new SimpleStringProperty(s.toString());
		});
		veicoloTableColumn.setCellValueFactory((CellDataFeatures<Noleggio, String> param) -> {
			StringBuilder s=new StringBuilder();
			s.append(param.getValue().getVeicolo().getCasaProduttrice());
			s.append(" ");
			s.append(param.getValue().getVeicolo().getNome());
			s.append("\n");
			return new SimpleStringProperty(s.toString());
		}); 
		dataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
		dataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
		idTableColumn.setCellValueFactory((CellDataFeatures<Noleggio, String> param) -> {
			StringBuilder s=new StringBuilder();
			s.append(param.getValue().getVeicolo().getNome().toUpperCase());
			s.append(param.getValue().getId());
			return new SimpleStringProperty(s.toString());
		}); 
		try {
			List<Noleggio> noleggi=noleggioService.findNoleggiInCorso();
			ObservableList<Noleggio> noleggiData=FXCollections.observableArrayList(noleggi);
			noleggiInCorsoTable.setItems(noleggiData);
		}catch(BusinessException e) { 
			dispatcher.renderError(e);
		}
	}

}
