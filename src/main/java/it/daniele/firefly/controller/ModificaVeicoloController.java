package it.univaq.disim.oop.firefly.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Alimentazione;
import it.univaq.disim.oop.firefly.domain.Feedback;
import it.univaq.disim.oop.firefly.domain.StatoVeicolo;
import it.univaq.disim.oop.firefly.domain.TipologiaVeicolo;
import it.univaq.disim.oop.firefly.domain.Valutazione;
import it.univaq.disim.oop.firefly.domain.Veicolo;
import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;

public class ModificaVeicoloController implements Initializable, DataInitializable<Veicolo> {
	@FXML
	private Label titoloLabel;
	@FXML
	private TextField casaProduttriceTextField;
	@FXML
	private TextField nomeTextField;
	@FXML
	private TextField targaTextField;
	@FXML
	private TextField chilometraggioTextField;
	@FXML
	private ComboBox<Integer> annoImmatricolazioneComboBox;
	@FXML
	private TextField tariffaBreveTermineTextField;
	@FXML
	private TextField tariffaMedioTermineTextField;
	@FXML
	private TextField tariffaLungoTermineTextField;
	@FXML
	private ComboBox<String> tipologiaVeicoloComboBox;
	@FXML
	private ComboBox<Alimentazione> alimentazioneComboBox;
	@FXML
	private ComboBox<StatoVeicolo> statoComboBox;
	@FXML
	private TextArea descrizioneTextArea;

	@FXML
	private Label errorLabel;
	@FXML
	private Veicolo veicolo;
	@FXML
	private SVGPath stella1;
	@FXML
	private SVGPath stella2;
	@FXML
	private SVGPath stella3;
	@FXML
	private SVGPath stella4;
	@FXML
	private SVGPath stella5;
	@FXML
	private VBox feedbackVBox;

	private VeicoloService veicoloService;
	private ViewDispatcher dispatcher;

	public ModificaVeicoloController() {

		dispatcher = ViewDispatcher.getInstance();
		FireFlyBusinessFactory factory = FireFlyBusinessFactory.getInstance();
		veicoloService = factory.getVeicoloService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		alimentazioneComboBox.getItems().addAll(Alimentazione.values());
		statoComboBox.getItems().addAll(StatoVeicolo.values());
		List<Integer> anni = new ArrayList<>();
		for (int i = LocalDate.now().getYear(); i >= 1850; i--) {
			anni.add(i);
		}
		annoImmatricolazioneComboBox.getItems().addAll(anni);// Uso la combobox per evitare controlli sul numero
		try {
			List<TipologiaVeicolo> tipologie = veicoloService.findAllTipologie();
			for (TipologiaVeicolo tv : tipologie) {
				tipologiaVeicoloComboBox.getItems().add(tv.getNomeTipologia());
			}
		} catch (BusinessException e) {
			dispatcher.renderError(e); 
		}

	}

	@Override
	public void initializeData(Veicolo veicolo) {
		this.veicolo = veicolo;
		if (veicolo.getId() != null) {
			titoloLabel.setText(veicolo.getCasaProduttrice() + " " + veicolo.getNome());
			nomeTextField.setText(veicolo.getNome());
			casaProduttriceTextField.setText(veicolo.getCasaProduttrice());
			targaTextField.setText(veicolo.getTarga());
			tipologiaVeicoloComboBox.setValue(veicolo.getTipologia().getNomeTipologia());
			chilometraggioTextField.setText(veicolo.getChilometraggio().toString());
			alimentazioneComboBox.setValue(veicolo.getAlimentazione());
			annoImmatricolazioneComboBox.setValue(veicolo.getAnnoImmatricolazione());
			tariffaBreveTermineTextField.setText(veicolo.getTariffaBreveTermine().toString());
			tariffaMedioTermineTextField.setText(veicolo.getTariffaMedioTermine().toString());
			tariffaLungoTermineTextField.setText(veicolo.getTariffaLungoTermine().toString());
			statoComboBox.setValue(veicolo.getStato());
			descrizioneTextArea.setText(veicolo.getDescrizione());

			try {
				float i = 0;
				for (Feedback feed : veicoloService.findAllFeedBack(veicolo)) {
					switch (feed.getValutazione()) {
					case UNO:
						i += 1;
						break;
					case DUE:
						i += 2;
						break;
					case TRE:
						i += 3;
						break;
					case QUATTRO:
						i += 4;
						break;
					case CINQUE:
						i += 5;
						break;
					}
				}
				i /= veicoloService.findAllFeedBack(veicolo).size();
				switch (Math.round(i)) {
				case 1:
					stella1.setStyle("-fx-fill:yellow;");
					stella1.setStroke(Paint.valueOf("#a2a40d"));
					stella1.setStrokeWidth(1.25);
					break;
				case 2:
					stella1.setStyle("-fx-fill:yellow;");
					stella2.setStyle("-fx-fill:yellow;");
					stella1.setStroke(Paint.valueOf("#a2a40d"));
					stella1.setStrokeWidth(1.25);
					stella2.setStroke(Paint.valueOf("#a2a40d"));
					stella2.setStrokeWidth(1.25);
					break;
				case 3:
					stella1.setStyle("-fx-fill:yellow;");
					stella2.setStyle("-fx-fill:yellow;");
					stella3.setStyle("-fx-fill:yellow;");
					stella1.setStroke(Paint.valueOf("#a2a40d"));
					stella1.setStrokeWidth(1.25);
					stella2.setStroke(Paint.valueOf("#a2a40d"));
					stella2.setStrokeWidth(1.25);
					stella3.setStroke(Paint.valueOf("#a2a40d"));
					stella3.setStrokeWidth(1.25);
					break;
				case 4:
					stella1.setStyle("-fx-fill:yellow;");
					stella2.setStyle("-fx-fill:yellow;");
					stella3.setStyle("-fx-fill:yellow;");
					stella4.setStyle("-fx-fill:yellow;");
					stella1.setStroke(Paint.valueOf("#a2a40d"));
					stella1.setStrokeWidth(1.25);
					stella2.setStroke(Paint.valueOf("#a2a40d"));
					stella2.setStrokeWidth(1.25);
					stella3.setStroke(Paint.valueOf("#a2a40d"));
					stella3.setStrokeWidth(1.25);
					stella4.setStroke(Paint.valueOf("#a2a40d"));
					stella4.setStrokeWidth(1.25);
					break;
				case 5:
					stella1.setStyle("-fx-fill:yellow;");
					stella2.setStyle("-fx-fill:yellow;");
					stella3.setStyle("-fx-fill:yellow;");
					stella4.setStyle("-fx-fill:yellow;");
					stella5.setStyle("-fx-fill:yellow;");
					stella1.setStroke(Paint.valueOf("#a2a40d"));
					stella1.setStrokeWidth(1.25);
					stella2.setStroke(Paint.valueOf("#a2a40d"));
					stella2.setStrokeWidth(1.25);
					stella3.setStroke(Paint.valueOf("#a2a40d"));
					stella3.setStrokeWidth(1.25);
					stella4.setStroke(Paint.valueOf("#a2a40d"));
					stella4.setStrokeWidth(1.25);
					stella5.setStroke(Paint.valueOf("#a2a40d"));
					stella5.setStrokeWidth(1.25);

					break;
				}
				int contatore = 1;
				for (Feedback feed : veicoloService.findAllFeedBack(veicolo)) {
					Label titolo = new Label();
					titolo.setFont(new Font("System", 15));
					titolo.setText("Feedback n°" + contatore);
					feedbackVBox.getChildren().add(titolo);
					contatore++;
					Separator separator = new Separator(Orientation.HORIZONTAL);
					feedbackVBox.getChildren().add(separator);
					Label cliente = new Label();
					cliente.setFont(new Font("System", 14));
					StringBuilder client = new StringBuilder();
					client.append(feed.getRilasciatoDa().getNome());
					client.append(" ");
					client.append(feed.getRilasciatoDa().getCognome());
					cliente.setText(client.toString());
					feedbackVBox.getChildren().add(cliente);
					feedbackVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
					HBox stelleBox = new HBox();
					final String STELLA = "M9.5 14.25l-5.584 2.936 1.066-6.218L.465 6.564l6.243-.907L9.5 0l2.792 5.657 6.243.907-4.517 4.404 1.066 6.218";
					SVGPath stellaUno = new SVGPath();
					SVGPath stellaDue = new SVGPath();
					SVGPath stellaTre = new SVGPath();
					SVGPath stellaQuattro = new SVGPath();
					SVGPath stellaCinque = new SVGPath();
					stellaUno.setContent(STELLA);
					stellaDue.setContent(STELLA);
					stellaTre.setContent(STELLA);
					stellaQuattro.setContent(STELLA);
					stellaCinque.setContent(STELLA);

					if (feed.getValutazione() == Valutazione.UNO) {
						stellaUno.setStyle("-fx-fill:yellow;");
						stellaUno.setStroke(Paint.valueOf("#a2a40d"));
						stellaUno.setStrokeWidth(1.25);

					}
					if (feed.getValutazione() == Valutazione.DUE) {
						stellaUno.setStyle("-fx-fill:yellow;");
						stellaUno.setStroke(Paint.valueOf("#a2a40d"));
						stellaUno.setStrokeWidth(1.25);
						stellaDue.setStyle("-fx-fill:yellow;");
						stellaDue.setStroke(Paint.valueOf("#a2a40d"));
						stellaDue.setStrokeWidth(1.25);

					}
					if (feed.getValutazione() == Valutazione.TRE) {
						stellaUno.setStyle("-fx-fill:yellow;");
						stellaUno.setStroke(Paint.valueOf("#a2a40d"));
						stellaUno.setStrokeWidth(1.25);
						stellaDue.setStyle("-fx-fill:yellow;");
						stellaDue.setStroke(Paint.valueOf("#a2a40d"));
						stellaDue.setStrokeWidth(1.25);
						stellaTre.setStyle("-fx-fill:yellow;");
						stellaTre.setStroke(Paint.valueOf("#a2a40d"));
						stellaTre.setStrokeWidth(1.25);
					}
					if (feed.getValutazione() == Valutazione.QUATTRO) {
						stellaUno.setStyle("-fx-fill:yellow;");
						stellaUno.setStroke(Paint.valueOf("#a2a40d"));
						stellaUno.setStrokeWidth(1.25);
						stellaDue.setStyle("-fx-fill:yellow;");
						stellaDue.setStroke(Paint.valueOf("#a2a40d"));
						stellaDue.setStrokeWidth(1.25);
						stellaTre.setStyle("-fx-fill:yellow;");
						stellaTre.setStroke(Paint.valueOf("#a2a40d"));
						stellaTre.setStrokeWidth(1.25);
						stellaQuattro.setStyle("-fx-fill:yellow;");
						stellaQuattro.setStroke(Paint.valueOf("#a2a40d"));
						stellaQuattro.setStrokeWidth(1.25);
					}
					if (feed.getValutazione() == Valutazione.CINQUE) {
						stellaUno.setStyle("-fx-fill:yellow;");
						stellaUno.setStroke(Paint.valueOf("#a2a40d"));
						stellaUno.setStrokeWidth(1.25);
						stellaDue.setStyle("-fx-fill:yellow;");
						stellaDue.setStroke(Paint.valueOf("#a2a40d"));
						stellaDue.setStrokeWidth(1.25);
						stellaTre.setStyle("-fx-fill:yellow;");
						stellaTre.setStroke(Paint.valueOf("#a2a40d"));
						stellaTre.setStrokeWidth(1.25);
						stellaQuattro.setStyle("-fx-fill:yellow;");
						stellaQuattro.setStroke(Paint.valueOf("#a2a40d"));
						stellaQuattro.setStrokeWidth(1.25);
						stellaCinque.setStyle("-fx-fill:yellow;");
						stellaCinque.setStroke(Paint.valueOf("#a2a40d"));
						stellaCinque.setStrokeWidth(1.25);
					}
					stelleBox.getChildren().add(stellaUno);
					stelleBox.getChildren().add(stellaDue);
					stelleBox.getChildren().add(stellaTre);
					stelleBox.getChildren().add(stellaQuattro);
					stelleBox.getChildren().add(stellaCinque);
					// Decisamente esiste una via migliore di questa
					feedbackVBox.getChildren().add(stelleBox);
					feedbackVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
					StringBuilder descrizione = new StringBuilder();
					descrizione.append(feed.getTesto());
					String descr = descrizione.toString().replaceAll("(.{30})", "$1\n");
					Label desc = new Label();
					desc.setFont(new Font("System", 14));
					desc.setText(descr);
					feedbackVBox.getChildren().add(desc);
					feedbackVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
					feedbackVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
				}

			} catch (BusinessException e) {
			}
		} else
			titoloLabel.setText("Nuovo veicolo:");
	}

	@FXML
	public void salvaAction(ActionEvent e) {
		if (nomeTextField.getText().contains(",") || casaProduttriceTextField.getText().contains(",")
				|| targaTextField.getText().contains(",") || chilometraggioTextField.getText().contains(",")
				|| tariffaBreveTermineTextField.getText().contains(",")
				|| tariffaMedioTermineTextField.getText().contains(",")
				|| tariffaLungoTermineTextField.getText().contains(",") || descrizioneTextArea.getText().contains(",")
				|| !chilometraggioTextField.getText().matches("[0-9]+")// Espressione regolare per vedere se una Stringa
																		// è un intero
				|| !tariffaBreveTermineTextField.getText().matches("-?\\d+(\\.\\d+)?")// Espressione regolare(rubata da
																						// internet) per vedere se la
																						// Stringa è un numero
				|| !tariffaMedioTermineTextField.getText().matches("-?\\d+(\\.\\d+)?")
				|| !tariffaLungoTermineTextField.getText().matches("-?\\d+(\\.\\d+)?"))
			errorLabel.setText(
					"Nessuno dei campi deve contenere il carattere \",\" \n I campi:chilometraggio e le tariffe devono essere valori numerici");
		else {

			veicolo.setNome(nomeTextField.getText());
			veicolo.setCasaProduttrice(casaProduttriceTextField.getText());
			veicolo.setTarga(targaTextField.getText());
			try {
				veicolo.setTipologia(veicoloService.findTipologiaByNome(tipologiaVeicoloComboBox.getValue()));
			} catch (BusinessException ex) {
				dispatcher.renderError(ex);
			}
			veicolo.setChilometraggio(Integer.parseInt(chilometraggioTextField.getText()));

			veicolo.setAnnoImmatricolazione(annoImmatricolazioneComboBox.getValue());
			veicolo.setTariffaBreveTermine(Double.parseDouble(tariffaBreveTermineTextField.getText()));
			veicolo.setTariffaMedioTermine(Double.parseDouble(tariffaMedioTermineTextField.getText()));
			veicolo.setTariffaLungoTermine(Double.parseDouble(tariffaLungoTermineTextField.getText()));
			veicolo.setStato(statoComboBox.getValue());
			veicolo.setDescrizione(descrizioneTextArea.getText());
			veicolo.setAlimentazione(alimentazioneComboBox.getValue());

			if (veicolo.getId() == null) {
				
				try {
					veicoloService.createVeicolo(veicolo);
				} catch (BusinessException ex) {
					dispatcher.renderError(ex);
				}
				dispatcher.renderView("veicoli", null);
			} else {
				try {
					veicoloService.updateVeicolo(veicolo);
				} catch (BusinessException ex) {
					dispatcher.renderError(ex);
				}
				dispatcher.renderView("veicoli", null);

			}

		}
	}

	public void annullaAction(ActionEvent e) {
		dispatcher.renderView("veicoli", null);
	}

	@FXML
	public void eliminaVeicoloAction(ActionEvent e) {
		try {
			veicoloService.deleteVeicolo(veicolo);
			dispatcher.renderView("veicoli", null);
		} catch (BusinessException ex) {
			dispatcher.renderError(ex);
		}
	}

}
