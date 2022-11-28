package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Feedback;
import it.univaq.disim.oop.firefly.domain.Valutazione;
import it.univaq.disim.oop.firefly.domain.Veicolo;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class FeedbackController implements Initializable, DataInitializable<Object> {
	@FXML
	private TableView<Feedback> feedbackTableView;
	@FXML
	private TableColumn<Feedback, String> clienteTableColumn;

	@FXML
	private TableColumn<Feedback, String> veicoloTableColumn;
	@FXML
	private TableColumn<Feedback, HBox> valutazioneTableColumn;
	@FXML
	private TableColumn<Feedback, String> testoTableColumn;
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloService;

	public FeedbackController() {
		this.dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		veicoloService = factory.getVeicoloService();
	} 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clienteTableColumn.setCellValueFactory((CellDataFeatures<Feedback,String> param)->{
			StringBuilder s=new StringBuilder();
			
			s.append(param.getValue().getRilasciatoDa().getNome());
			s.append(" ");
			s.append(param.getValue().getRilasciatoDa().getCognome());
			return new SimpleStringProperty(s.toString());
		});
		veicoloTableColumn.setCellValueFactory((CellDataFeatures<Feedback,String> param)->{
			StringBuilder s=new StringBuilder();
			
			s.append(param.getValue().getVeicolo().getCasaProduttrice());
			s.append(" ");
			s.append(param.getValue().getVeicolo().getNome());
			s.append("\n");
			s.append(param.getValue().getVeicolo().getTarga());
			return new SimpleStringProperty(s.toString());
		});
		
		valutazioneTableColumn.setCellValueFactory((CellDataFeatures<Feedback,HBox> param)->{
			HBox box=new HBox();
			final String STELLA="M9.5 14.25l-5.584 2.936 1.066-6.218L.465 6.564l6.243-.907L9.5 0l2.792 5.657 6.243.907-4.517 4.404 1.066 6.218";
			SVGPath stella1=new SVGPath();
			SVGPath stella2=new SVGPath();
			SVGPath stella3=new SVGPath();
			SVGPath stella4=new SVGPath();
			SVGPath stella5=new SVGPath();
			stella1.setContent(STELLA);
			stella2.setContent(STELLA);
			stella3.setContent(STELLA);
			stella4.setContent(STELLA);
			stella5.setContent(STELLA);
			
			
			//TODO Sta cosa è un pò una merda (i bordi sono colorati malissimo [sistemare prima della consegna])
			
			
			if(param.getValue().getValutazione()==Valutazione.UNO) {
				stella1.setStyle("-fx-fill:yellow;");
				stella1.setStroke(Paint.valueOf("#a2a40d"));
				stella1.setStrokeWidth(1.25);
				
			}
			if(param.getValue().getValutazione()==Valutazione.DUE) {
				stella1.setStyle("-fx-fill:yellow;");
				stella1.setStroke(Paint.valueOf("#a2a40d"));
				stella1.setStrokeWidth(1.25);
				stella2.setStyle("-fx-fill:yellow;");
				stella2.setStroke(Paint.valueOf("#a2a40d"));
				stella2.setStrokeWidth(1.25);
				
			}
			if(param.getValue().getValutazione()==Valutazione.TRE) {
				stella1.setStyle("-fx-fill:yellow;");
				stella1.setStroke(Paint.valueOf("#a2a40d"));
				stella1.setStrokeWidth(1.25);
				stella2.setStyle("-fx-fill:yellow;");
				stella2.setStroke(Paint.valueOf("#a2a40d"));
				stella2.setStrokeWidth(1.25);
				stella3.setStyle("-fx-fill:yellow;");
				stella3.setStroke(Paint.valueOf("#a2a40d"));
				stella3.setStrokeWidth(1.25);
			}
			if(param.getValue().getValutazione()==Valutazione.QUATTRO) {
				stella1.setStyle("-fx-fill:yellow;");
				stella1.setStroke(Paint.valueOf("#a2a40d"));
				stella1.setStrokeWidth(1.25);
				stella2.setStyle("-fx-fill:yellow;");
				stella2.setStroke(Paint.valueOf("#a2a40d"));
				stella2.setStrokeWidth(1.25);
				stella3.setStyle("-fx-fill:yellow;");
				stella3.setStroke(Paint.valueOf("#a2a40d"));
				stella3.setStrokeWidth(1.25);
				stella4.setStyle("-fx-fill:yellow;");
				stella4.setStroke(Paint.valueOf("#a2a40d"));
				stella4.setStrokeWidth(1.25);
			}
			if(param.getValue().getValutazione()==Valutazione.CINQUE) {
				stella1.setStyle("-fx-fill:yellow;");
				stella1.setStroke(Paint.valueOf("#a2a40d"));
				stella1.setStrokeWidth(1.25);
				stella2.setStyle("-fx-fill:yellow;");
				stella2.setStroke(Paint.valueOf("#a2a40d"));
				stella2.setStrokeWidth(1.25);
				stella3.setStyle("-fx-fill:yellow;");
				stella3.setStroke(Paint.valueOf("#a2a40d"));
				stella3.setStrokeWidth(1.25);
				stella4.setStyle("-fx-fill:yellow;"); 
				stella4.setStroke(Paint.valueOf("#a2a40d"));
				stella4.setStrokeWidth(1.25);
				stella5.setStyle("-fx-fill:yellow;");
				stella5.setStroke(Paint.valueOf("#a2a40d"));
				stella5.setStrokeWidth(1.25);
			}
			box.getChildren().add(stella1);
			box.getChildren().add(stella2);
			box.getChildren().add(stella3);
			box.getChildren().add(stella4);
			box.getChildren().add(stella5);
            
            return new SimpleObjectProperty<HBox>(box);
			
            
		});
		testoTableColumn.setCellValueFactory(new PropertyValueFactory<>("testo"));
		try {
		List<Feedback> lista=veicoloService.findAllFeedback();
		
		ObservableList<Feedback>feedbackData=FXCollections.observableArrayList(lista);
		feedbackTableView.setItems(feedbackData);
		}catch(BusinessException ex) {
			dispatcher.renderError(ex);
		}
	}

}
