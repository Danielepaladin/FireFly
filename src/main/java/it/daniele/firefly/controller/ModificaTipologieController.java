package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.CampoDiUtilizzo;
import it.univaq.disim.oop.firefly.domain.TipologiaVeicolo;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ModificaTipologieController implements Initializable,DataInitializable<TipologiaVeicolo> {
	@FXML
	private Label titoloLabel;
	@FXML
	private TextField nomeTextField;
	@FXML
	private ComboBox<String> campoUtilizzoComboBox;
	@FXML
	private TextArea caratteristicheTextArea;
	@FXML
	private Label errorLabel;
	
	private ViewDispatcher dispatcher;
	private VeicoloService veicoloService;
	private TipologiaVeicolo tipologia;
	public ModificaTipologieController() {
		dispatcher=ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory=FireFlyBusinessFactory.getInstance();
		veicoloService=factory.getVeicoloService();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		campoUtilizzoComboBox.getItems().add("Trasporto Persone");
		campoUtilizzoComboBox.getItems().add("Trasporto Animali");
		campoUtilizzoComboBox.getItems().add("Trasporto Merci");
		
		
	}
	@Override
	public void initializeData(TipologiaVeicolo tipologia) {
		this.tipologia=tipologia;
		if (tipologia.getId()==null) {
			titoloLabel.setText("Nuova tipologia di veicolo");
		}else {
			nomeTextField.setText(tipologia.getNomeTipologia());
			String str="";
			for (CampoDiUtilizzo c: CampoDiUtilizzo.values()){
				if (tipologia.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOPERSONE))
					str="Trasporto Persone";
				if (tipologia.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOMERCI))
					str="Trasporto Merci";
				if (tipologia.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOANIMALI))
					str="Trasporto Animali";
			}
			campoUtilizzoComboBox.setValue(str);
			StringBuilder s=new StringBuilder();
			for (String st:tipologia.getCaratteristiche()) {
				s.append(st);
				s.append(";");
			}
			caratteristicheTextArea.setText(s.toString());
		}
	}
	@FXML
	public void salvaAction(ActionEvent e) {
		if(nomeTextField.getText().contains(",")||caratteristicheTextArea.getText().contains(",")) {
			errorLabel.setText("Nessuno dei campi deve contenre il carattere \",\"");
		}else {
			tipologia.setNomeTipologia(nomeTextField.getText());
			if (campoUtilizzoComboBox.getValue().equals("Trasporto Animali"))
				tipologia.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOANIMALI);
			if (campoUtilizzoComboBox.getValue().equals("Trasporto Persone"))
				tipologia.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOPERSONE);
			if (campoUtilizzoComboBox.getValue().equals("Trasporto Merci"))
				tipologia.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOMERCI);
			String s=caratteristicheTextArea.getText();//faccio inserire le caratteristiche separate da ; 
			
			List<String> temp=Arrays.asList(s.split(";"));
			for(String t:temp) {
				if (t.isEmpty())
						temp.remove(t);
			}
			tipologia.setCaratteristiche(temp);
			if (tipologia.getId()==null) {
				try {
					veicoloService.createTipologiaVeicolo(tipologia);
				}catch(BusinessException ex) {
					dispatcher.renderError(ex);
				}
			}else {
				try {
					veicoloService.updateTipologiaVeicolo(tipologia);
				}catch(BusinessException ex) {
					dispatcher.renderError(ex);
				}
			}
			dispatcher.renderView("tipologieVeicoli", null);
		}
	}
	@FXML
	public void annullaAction(ActionEvent e) {
		dispatcher.renderView("tipologieVeicoli", null);
	}

}
