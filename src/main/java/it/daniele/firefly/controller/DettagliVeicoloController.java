package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Alimentazione;
import it.univaq.disim.oop.firefly.domain.StatoVeicolo;
import it.univaq.disim.oop.firefly.domain.Veicolo;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DettagliVeicoloController implements Initializable, DataInitializable<Veicolo> {

	@FXML
	private Label nomeLabel;
	@FXML
	private Label casaProduttriceLabel;
	@FXML
	private Label targaLabel;
	@FXML
	private Label annoImmatricolazioneLabel;
	@FXML
	private Label alimentazioneLabel;
	@FXML
	private Label chilometraggioLabel;
	@FXML
	private Label tariffeLabel;
	@FXML
	private Label statoLabel;
	@FXML
	private Label tipologiaLabel;
	@FXML
	private Label descrizioneLabel;
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloService;
	private Veicolo veicolo; 
	
	public DettagliVeicoloController() {
		dispatcher=ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory=FireFlyBusinessFactory.getInstance();
		veicoloService=factory.getVeicoloService();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	@Override
	public void initializeData(Veicolo veicolo) {
		nomeLabel.setText("Nome veicolo : "+veicolo.getNome());
		casaProduttriceLabel.setText("Casa Produttrice: "+veicolo.getCasaProduttrice());
		targaLabel.setText("Targa: "+veicolo.getTarga());
		annoImmatricolazioneLabel.setText("Anno immatricolazione: "+veicolo.getAnnoImmatricolazione().toString());
		StringBuilder s =new StringBuilder();
		s.append("Alimentazione: ");
		if(veicolo.getAlimentazione().equals(Alimentazione.BENZINA))
			s.append("Benzina");
		if(veicolo.getAlimentazione().equals(Alimentazione.DIESEL))
			s.append("Diesel");
		if(veicolo.getAlimentazione().equals(Alimentazione.ELETTRICO))
			s.append("Elettrica");
		if(veicolo.getAlimentazione().equals(Alimentazione.GPL))
			s.append("GPL");
		alimentazioneLabel.setText(s.toString());
		chilometraggioLabel.setText("Chilometraggio :"+veicolo.getChilometraggio().toString());
		StringBuilder tariffe=new StringBuilder();
		tariffe.append("Tariffe: ");
		tariffe.append("\n");
		tariffe.append("Breve periodo");
		tariffe.append(veicolo.getTariffaBreveTermine().toString());
		tariffe.append("\n");
		tariffe.append("Medio periodo: ");
		tariffe.append(veicolo.getTariffaMedioTermine());
		tariffe.append("\n");
		tariffe.append("Lungo periodo:");
		tariffe.append(veicolo.getTariffaLungoTermine());
		tariffeLabel.setText(tariffe.toString());
		StringBuilder stato =new StringBuilder();
		stato.append("Stato Veicolo: ");
		if(veicolo.getStato().equals(StatoVeicolo.DISPONIBILE))
			stato.append("Disponibile");
		if(veicolo.getStato().equals(StatoVeicolo.MANUTENZIONE))
			s.append("Manutenzione in corso");
		if(veicolo.getStato().equals(StatoVeicolo.NOLEGGIO))
			s.append("Noleggio in corso");
		
		statoLabel.setText(stato.toString());
		tipologiaLabel.setText("Tipologia Veicolo: "+veicolo.getTipologia().getNomeTipologia());
		String descrizione=veicolo.getDescrizione();
		String temp=descrizione.replaceAll("(.{40})", "$1\n"); //aggiunge /n ogni 40 caratteri
		descrizioneLabel.setText(temp);
		
	}
	@FXML
	public void confermaAction(ActionEvent e) {
		dispatcher.renderView("veicoliOperatore", null);
	}

}
