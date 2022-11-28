package it.univaq.disim.oop.firefly.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Alimentazione;
import it.univaq.disim.oop.firefly.domain.CampoDiUtilizzo;
import it.univaq.disim.oop.firefly.domain.Feedback;
import it.univaq.disim.oop.firefly.domain.StatoVeicolo;
import it.univaq.disim.oop.firefly.domain.TipologiaVeicolo;
import it.univaq.disim.oop.firefly.domain.Valutazione;
import it.univaq.disim.oop.firefly.domain.Veicolo;

public class FileVeicoloService implements VeicoloService {
	private String veicoliFilename;
	private String tipologieVeicoloFilename;
	private String feedbackFileName;
	private UtenteService utenteService;

	public FileVeicoloService(String veicoliFileName, String tipologieVeicoloFilename, String feedbackFileName,UtenteService utenteService) {
		this.veicoliFilename = veicoliFileName;
		this.tipologieVeicoloFilename = tipologieVeicoloFilename;
		this.feedbackFileName=feedbackFileName;
		this.utenteService=utenteService;
	}

//id,nome,casaP,targa,alimentazione,annoImmatricolazione,tarriffaB,tariffaM,tariffaL,stato,chilometraggio,descrizione,feed,tipologia
	@Override
	public List<Veicolo> findAllVeicoli() throws BusinessException {
		List<Veicolo> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(veicoliFilename);
			for (String[] colonne : fileData.getRighe()) {
				Veicolo veicolo = new Veicolo(); 
				veicolo.setId(Integer.parseInt(colonne[0]));
				veicolo.setNome(colonne[1]);
				veicolo.setCasaProduttrice(colonne[2]);
				veicolo.setTarga(colonne[3]);
				veicolo.setAlimentazione(Alimentazione.valueOf(colonne[4]));
				
				veicolo.setAnnoImmatricolazione(Integer.parseInt(colonne[5]));
				veicolo.setTariffaBreveTermine(Double.parseDouble(colonne[6]));
				veicolo.setTariffaMedioTermine(Double.parseDouble(colonne[7]));
				veicolo.setTariffaLungoTermine(Double.parseDouble(colonne[8]));
				veicolo.setStato(StatoVeicolo.valueOf(colonne[9]));
				veicolo.setChilometraggio(Integer.parseInt(colonne[10]));
				veicolo.setDescrizione(colonne[11]);
				veicolo.setTipologia(findTipologiaById(Integer.parseInt(colonne[12])));
				result.add(veicolo);

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		return result;
	}

	@Override
	public Veicolo findVeicoloById(int id) throws BusinessException {
		Veicolo result = new Veicolo();
		try {
			FileData fileData = Utility.readAllRows(veicoliFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					result.setId(Integer.parseInt(colonne[0]));
					result.setNome(colonne[1]);
					result.setCasaProduttrice(colonne[2]);
					result.setTarga(colonne[3]);
					result.setAlimentazione(Alimentazione.valueOf(colonne[4]));
					result.setAnnoImmatricolazione(Integer.parseInt(colonne[5]));
					result.setTariffaBreveTermine(Double.parseDouble(colonne[6]));
					result.setTariffaMedioTermine(Double.parseDouble(colonne[7]));
					result.setTariffaLungoTermine(Double.parseDouble(colonne[8]));
					result.setStato(StatoVeicolo.valueOf(colonne[9]));
					result.setChilometraggio(Integer.parseInt(colonne[10]));
					result.setDescrizione(colonne[11]);
					result.setTipologia(findTipologiaById(Integer.parseInt(colonne[12])));
					return result;

				}
			}
		} catch (IOException e) {

			e.printStackTrace();
			throw new BusinessException();
		}

		return result;

	}

	@Override
	public void deleteVeicolo(Veicolo veicolo) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(veicoliFilename);
			try (PrintWriter writer = new PrintWriter(new File(veicoliFilename))) {
				writer.println(fileData.getContatore() + 1);
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == veicolo.getId()) {
						righe[0] = "";
						righe[0].trim();
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public void createVeicolo(Veicolo veicolo) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(veicoliFilename);
			try (PrintWriter writer = new PrintWriter(new File(veicoliFilename))) {
				long contatore = fileData.getContatore();
				writer.println(contatore + 1);
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				// id,nome,casaP,targa,alimentazione,annoImmatricolazione,tarriffaB,tariffaM,tariffaL,stato,chilometraggio,descrizione,feed,tipologia
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getCasaProduttrice());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getTarga());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getAlimentazione());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getAnnoImmatricolazione());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getTariffaBreveTermine());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getTariffaMedioTermine());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getTariffaLungoTermine());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getStato());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getChilometraggio());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getDescrizione());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(veicolo.getTipologia().getId());
				writer.println(row.toString());

			}
		} catch (IOException e) {
		}

	}

	@Override
	public void updateVeicolo(Veicolo veicolo) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(veicoliFilename);
			try (PrintWriter writer = new PrintWriter(new File(veicoliFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == veicolo.getId()) {
						StringBuilder row = new StringBuilder();
//id,nome,casaP,targa,alimentazione,annoImmatricolazione,tarriffaB,tariffaM,tariffaL,stato,chilometraggio,descrizione,feed,tipologia
						row.append(veicolo.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getCasaProduttrice());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getTarga());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getAlimentazione());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getAnnoImmatricolazione());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getTariffaBreveTermine());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getTariffaMedioTermine());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getTariffaLungoTermine());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getStato());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getChilometraggio());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getDescrizione());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(veicolo.getTipologia().getId());
						// tipologia
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}

	}

	@Override
	public List<TipologiaVeicolo> findAllTipologie() throws BusinessException {
		List<TipologiaVeicolo> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(tipologieVeicoloFilename);
			for (String[] colonne : fileData.getRighe()) {
				TipologiaVeicolo tipologia = new TipologiaVeicolo();
				tipologia.setId(Integer.parseInt(colonne[0]));
				switch (colonne[1]) {
				case "0":
					tipologia.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOPERSONE);
					break;
				case "1":
					tipologia.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOMERCI);
					break;
				case "2":
					tipologia.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOANIMALI);
					break;
				}
				List<String> temp = new ArrayList<>();
				temp = Arrays.asList(colonne[2].split(";"));
				tipologia.setCaratteristiche(temp);
				tipologia.setNomeTipologia(colonne[3]);
				result.add(tipologia);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

//ID,CampoUtilizzoID,caratteristica1;caratteristica2;,nome
	@Override
	public TipologiaVeicolo findTipologiaById(int id) throws BusinessException {
		TipologiaVeicolo result = new TipologiaVeicolo();
		try {
			FileData fileData = Utility.readAllRows(tipologieVeicoloFilename);
			for (String[] colonne : fileData.getRighe())
				if (Integer.parseInt(colonne[0]) == id) {
					result.setId(id);
					switch (colonne[1]) {
					case "0":
						result.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOPERSONE);
						break;
					case "1":
						result.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOMERCI);
						break;
					case "2":
						result.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOANIMALI);
						break;
					}
					List<String> temp = new ArrayList<>();
					temp = Arrays.asList(colonne[2].split(";"));
					result.setCaratteristiche(temp);
					result.setNomeTipologia(colonne[3]);
					return result;

				}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;

	}

	@Override
	public void createTipologiaVeicolo(TipologiaVeicolo tipologiaVeicolo) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(tipologieVeicoloFilename);
			try (PrintWriter writer = new PrintWriter(new File(tipologieVeicoloFilename))) {
				long contatore = fileData.getContatore();
				writer.println(contatore + 1);
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				if (tipologiaVeicolo.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOPERSONE))
					row.append("0");
				if (tipologiaVeicolo.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOMERCI))
					row.append("1");
				if (tipologiaVeicolo.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOANIMALI))
					row.append("2");// Penso Ok
				row.append(Utility.SEPARATORE_COLONNA);
				String s = String.join(";", tipologiaVeicolo.getCaratteristiche());

				row.append(s);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(tipologiaVeicolo.getNomeTipologia());

				writer.println(row.toString());

			}
		} catch (IOException e) {
		}

	}

	@Override
	public void updateTipologiaVeicolo(TipologiaVeicolo tipologiaVeicolo) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(tipologieVeicoloFilename);
			try (PrintWriter writer = new PrintWriter(new File(tipologieVeicoloFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == tipologiaVeicolo.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(tipologiaVeicolo.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						if (tipologiaVeicolo.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOPERSONE))
							row.append("0");
						if (tipologiaVeicolo.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOMERCI))
							row.append("1");
						if (tipologiaVeicolo.getCampoDiUtilizzo().equals(CampoDiUtilizzo.TRASPORTOANIMALI))
							row.append("2");// Penso Ok
						row.append(Utility.SEPARATORE_COLONNA);
						String s = String.join(";", tipologiaVeicolo.getCaratteristiche());

						row.append(s);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(tipologiaVeicolo.getNomeTipologia());

						writer.println(row.toString());
					}else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}

	}

	@Override
	public TipologiaVeicolo findTipologiaByNome(String nome) throws BusinessException {
		TipologiaVeicolo result = new TipologiaVeicolo();
		try {
			FileData fileData = Utility.readAllRows(tipologieVeicoloFilename);
			for (String[] colonne : fileData.getRighe())
				if (colonne[3].equals(nome)) {
					result.setId(Integer.parseInt(colonne[0]));
					switch (colonne[1]) {
					case "0":
						result.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOPERSONE);
						break;
					case "1":
						result.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOMERCI);
						break;
					case "2":
						result.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOANIMALI);
						break;
					}
					List<String> temp = new ArrayList<>();
					temp = Arrays.asList(colonne[2].split(";"));
					result.setCaratteristiche(temp);
					result.setNomeTipologia(colonne[3]);
					return result;

				}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;

	}

	@Override
	public void deleteTipologiaVeicolo(TipologiaVeicolo tipologia) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(tipologieVeicoloFilename);
			try (PrintWriter writer = new PrintWriter(new File(tipologieVeicoloFilename))) {
				writer.println(fileData.getContatore() + 1);
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == tipologia.getId()) {
						righe[0] = "";
						righe[0].trim();
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	 
	//FeedBack file:
	//id,veicoloid,clienteid,valutazione,testo
	// 0,    1,          2,       3,        4
	@Override
	public List<Feedback> findAllFeedBack(Veicolo v) throws BusinessException {
		List<Feedback> result=new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(feedbackFileName);
			for (String[] colonne : fileData.getRighe()) {
				if(Integer.parseInt(colonne[1])==v.getId()){
				Feedback feed=new Feedback();
				feed.setId(Integer.parseInt(colonne[0]));
				feed.setVeicolo(v);
				feed.setRilasciatoDa(utenteService.findClienteById(Integer.parseInt(colonne[2])));
				feed.setValutazione(Valutazione.valueOf(colonne[3]));
				feed.setTesto(colonne[4]);
				result.add(feed);
			}
				}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
		
		
		
	}
	@Override
	public List<Feedback> findAllFeedback() throws BusinessException {
		List<Feedback> result=new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(feedbackFileName);
			for (String[] colonne : fileData.getRighe()) {
				Feedback feed=new Feedback();
				feed.setId(Integer.parseInt(colonne[0]));
				feed.setVeicolo(findVeicoloById(Integer.parseInt(colonne[1])));
				feed.setRilasciatoDa(utenteService.findClienteById(Integer.parseInt(colonne[2])));
				feed.setValutazione(Valutazione.valueOf(colonne[3]));
				feed.setTesto(colonne[4]);
				result.add(feed);
			
				}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
		
		
		
	}

	@Override
	public void deleteFeedback(Feedback feed) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(feedbackFileName);
			try (PrintWriter writer = new PrintWriter(new File(feedbackFileName))) {
				writer.println(fileData.getContatore() + 1);
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == feed.getId()) {
						righe[0] = "";
						righe[0].trim();
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public void updateFeedBack(Feedback feedback) throws BusinessException {
		
		try {
			FileData fileData = Utility.readAllRows(feedbackFileName);
			try (PrintWriter writer = new PrintWriter(new File(feedbackFileName))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == feedback.getId()) {
						StringBuilder row = new StringBuilder();
						//id,veicoloid,clienteid,valutazione,testo
						row.append(feedback.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(feedback.getVeicolo().getId().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(feedback.getRilasciatoDa().getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(feedback.getValutazione());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(feedback.getTesto());
						writer.println(row.toString());
					}
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public void createFeedback(Feedback feedback) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(feedbackFileName);
			try (PrintWriter writer = new PrintWriter(new File(feedbackFileName))) {
				long contatore = fileData.getContatore();
				writer.println(contatore + 1);
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore); 
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(feedback.getVeicolo().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(feedback.getRilasciatoDa().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(feedback.getValutazione());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(feedback.getTesto());
				writer.println(row.toString());

			}
		} catch (IOException e) {
		}

	}
}
