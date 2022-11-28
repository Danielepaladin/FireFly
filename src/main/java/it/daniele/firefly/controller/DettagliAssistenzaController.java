package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.domain.InterventoAssistenza;
import it.univaq.disim.oop.firefly.domain.TipoRichiesta;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DettagliAssistenzaController implements Initializable,DataInitializable<InterventoAssistenza>{
	@FXML
	private Label titoloLabel;
	@FXML
	private Label clienteLabel;
	@FXML
	private Label veicoloLabel;
	@FXML
	private Label dataRichiestaLabel;
	@FXML
	private Label tipoRichiestaLabel;
	@FXML
	private Label dataCompletamentoLabel;
	@FXML
	private Label descrizioneLabel;
	private ViewDispatcher dispatcher;
	
public DettagliAssistenzaController() {
	dispatcher=ViewDispatcher.getInstance();
}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	@Override
	public void initializeData(InterventoAssistenza intervento) {
		titoloLabel.setText("Dettagli interverno assistenza n°"+intervento.getId());
		veicoloLabel.setText(intervento.getVeicolo().getCasaProduttrice()+" "+intervento.getVeicolo().getNome());
		clienteLabel.setText(intervento.getCliente().getUsername());
		dataRichiestaLabel.setText(intervento.getDataRichiesta().toString());
		if (intervento.getCompletato()) {
			dataCompletamentoLabel.setText(intervento.getDataCompletamento().toString());
		}else {
			dataCompletamentoLabel.setText("Non completato");
		}
		switch(intervento.getTipoRichesta() ) {
		case FURTO:
			tipoRichiestaLabel.setText("Furto");
			break;
		case INCIDENTE:
			tipoRichiestaLabel.setText("Incidente");
			break;
		case GUASTO:
			tipoRichiestaLabel.setText("Guasto");
			break;
		}
		descrizioneLabel.setText(intervento.getDescrizione());
	}
	
	
	@FXML
	public void indietroAction(ActionEvent e) {
		dispatcher.renderView("assistenzaAmministratore", null);
		
	}



}
