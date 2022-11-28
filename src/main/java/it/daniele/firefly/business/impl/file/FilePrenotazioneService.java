package it.univaq.disim.oop.firefly.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.PrenotazioneService;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Notifica;
import it.univaq.disim.oop.firefly.domain.PrenotazioneNoleggio;
import it.univaq.disim.oop.firefly.domain.Preventivo;

public class FilePrenotazioneService implements PrenotazioneService {
	private String prenotazioniFileName;
	private VeicoloService veicoloService;
	private UtenteService utenteService;
	private String notificheFileName;

	public FilePrenotazioneService(String prenotazioniFileName, VeicoloService veicoloService,
			UtenteService utenteService, String notificheFilename) {
		this.prenotazioniFileName = prenotazioniFileName;
		this.utenteService = utenteService;
		this.veicoloService = veicoloService;
		this.notificheFileName=notificheFilename;
	}

	@Override
	public List<PrenotazioneNoleggio> findAllPrenotazioni() throws BusinessException {
		List<PrenotazioneNoleggio> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			for (String[] colonne : fileData.getRighe()) {
				// id,clienteid,veicoloid,dataInizio,dataFine
				PrenotazioneNoleggio prenotazione = new PrenotazioneNoleggio();
				prenotazione.setId(Integer.parseInt(colonne[0]));
				prenotazione.setVeicolo(veicoloService.findVeicoloById(Integer.parseInt(colonne[1])));
				prenotazione.setCliente(utenteService.findClienteById(Integer.parseInt(colonne[2])));
				prenotazione.setDataInizioNoleggio(LocalDate.parse(colonne[3]));
				prenotazione.setDataFineNoleggio(LocalDate.parse(colonne[4]));
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public void createPrenotazione(PrenotazioneNoleggio prenotazione) throws BusinessException {
		try {

			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			try (PrintWriter writer = new PrintWriter(new File(prenotazioniFileName))) {
				long contatore = fileData.getContatore();
				prenotazione.setId((int) contatore);
				writer.println(contatore + 1);
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prenotazione.getCliente().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prenotazione.getVeicolo().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prenotazione.getDataInizioNoleggio());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prenotazione.getDataFineNoleggio());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Preventivo> findAllPreventivi() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPreventivo() throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void inviaNotifica(Notifica notifica) throws BusinessException {
		
		try {
			FileData fileData = Utility.readAllRows(notificheFileName);
			try (PrintWriter writer = new PrintWriter(new File(notificheFileName))) {
				long contatore = fileData.getContatore();
				notifica.setId((int) contatore);
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				//id,clienteid,veicoloId,dataRitiro,oraRitiro,testo
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(notifica.getCliente().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(notifica.getVeicolo().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(notifica.getDataRitiro());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(notifica.getOraRitiro());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(notifica.getTesto());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	@Override
	public List<Notifica> findAllNotifiche(Cliente cliente) throws BusinessException {
		List<Notifica> result = new ArrayList<>();
		try {

			FileData fileData = Utility.readAllRows(notificheFileName);
			for (String[] colonne : fileData.getRighe()) {
				if (cliente.getId()==Integer.parseInt(colonne[1])) {
					Notifica notifica = new Notifica();
					notifica.setId(Integer.parseInt(colonne[0]));
					notifica.setCliente(utenteService.findClienteById(Integer.parseInt(colonne[1])));
					notifica.setVeicolo(veicoloService.findVeicoloById(Integer.parseInt(colonne[2])));
					notifica.setDataRitiro(LocalDate.parse(colonne[3]));
					notifica.setOraRitiro(LocalDateTime.parse(colonne[4]));
					notifica.setTesto(colonne[5]);
					result.add(notifica);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

}
