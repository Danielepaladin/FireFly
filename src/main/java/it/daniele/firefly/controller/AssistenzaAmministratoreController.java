package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.NoleggioService;
import it.univaq.disim.oop.firefly.domain.InterventoAssistenza;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;

public class AssistenzaAmministratoreController implements Initializable, DataInitializable<Object> {

	@FXML
	private TableView<InterventoAssistenza> assistenzaTable;
	@FXML
	private TableColumn<InterventoAssistenza, CheckBox> completatoTableColumn;
	@FXML
	private TableColumn<InterventoAssistenza, String> veicoloTableColumn;
	@FXML
	private TableColumn<InterventoAssistenza, String> clienteTableColumn;
	@FXML
	private TableColumn<InterventoAssistenza, String> tipoRichiestaTableColumn;
	@FXML
	private TableColumn<InterventoAssistenza, Button> dettagliTableColumn;

	private ViewDispatcher dispatcher;
	private NoleggioService noleggioService;

	public AssistenzaAmministratoreController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		noleggioService = factory.getNoleggioService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		completatoTableColumn.setCellValueFactory((CellDataFeatures<InterventoAssistenza,CheckBox>param)->{
			CheckBox completatoBox=new CheckBox();
			completatoBox.setSelected(param.getValue().getCompletato());
			completatoBox.setDisable(true);
			completatoBox.setOpacity(1);
			return new SimpleObjectProperty<CheckBox>(completatoBox);
		});
		
		veicoloTableColumn.setCellValueFactory((CellDataFeatures<InterventoAssistenza,String> param)->{
			StringBuilder s=new StringBuilder();
			s.append(param.getValue().getVeicolo().getCasaProduttrice());
			s.append(param.getValue().getVeicolo().getNome());
			s.append("\n");
			s.append(param.getValue().getVeicolo().getTarga());
			return new SimpleStringProperty(s.toString());
		});
		clienteTableColumn.setCellValueFactory((CellDataFeatures<InterventoAssistenza,String> param)->{
			String s=param.getValue().getCliente().getUsername();
			return new SimpleStringProperty(s);
		});
		tipoRichiestaTableColumn.setCellValueFactory((CellDataFeatures<InterventoAssistenza,String>param)->{
			StringBuilder s=new StringBuilder();
			switch(param.getValue().getTipoRichesta()) {
			case INCIDENTE:
				s.append("Incidente");
				break;
			case GUASTO:
				s.append("Guasto");
				break;
			case FURTO:
				s.append("Furto");
			}
			return new SimpleStringProperty(s.toString());
		});
		dettagliTableColumn.setCellValueFactory((CellDataFeatures<InterventoAssistenza, Button> param) -> {
			final Button dettagliButton = new Button("Dettagli");
			dettagliButton.setOnAction((ActionEvent event) -> {
				dispatcher.renderView("dettagliAssistenza", param.getValue());
			});
			return new SimpleObjectProperty<Button>(dettagliButton);
		});
	
	
	try {
		List<InterventoAssistenza>interventi=noleggioService.findAllInterventiAssistenza();
		ObservableList<InterventoAssistenza> assistenzaData=FXCollections.observableArrayList(interventi);
		assistenzaTable.setItems(assistenzaData);
	}catch(BusinessException e) {
		dispatcher.renderError(e);
		
	} 
	
	
	
	
	
	
	
	
	
	}

}
