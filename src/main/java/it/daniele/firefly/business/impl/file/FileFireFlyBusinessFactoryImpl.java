package it.univaq.disim.oop.firefly.business.impl.file;

import java.io.File;

import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.NoleggioService;
import it.univaq.disim.oop.firefly.business.PrenotazioneService;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;

public class FileFireFlyBusinessFactoryImpl extends FireFlyBusinessFactory {
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "dati";
	private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "utenti.txt";
	private static final String VEICOLI_FILE_NAME = REPOSITORY_BASE + File.separator + "veicoli.txt";
	private static final String TIPOLOGIA_VEICOLI_FILE_NAME = REPOSITORY_BASE + File.separator + "tipologieVeicoli.txt";
	private static final String NOLEGGI_FILE_NAME = REPOSITORY_BASE + File.separator + "noleggi.txt";
	private static final String FEEDBACK_FILE_NAME = REPOSITORY_BASE + File.separator + "feedback.txt";
	private static final String INTERVENTI_ASSISTENZA_FILE_NAME = REPOSITORY_BASE + File.separator + "assistenza.txt";
	private static final String PRENOTAZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "prentotazioni.txt";
	private static final String NOTIFICHE_FILE_NAME = REPOSITORY_BASE + File.separator + "notifiche.txt";
	private UtenteService utenteService; 
	private VeicoloService veicoloService; 
	private NoleggioService noleggioService;
	private PrenotazioneService prenotazioneService;

	public FileFireFlyBusinessFactoryImpl() {
		utenteService = new FileUtenteService(UTENTI_FILE_NAME);
		veicoloService = new FileVeicoloService(VEICOLI_FILE_NAME, TIPOLOGIA_VEICOLI_FILE_NAME,FEEDBACK_FILE_NAME,utenteService);
		noleggioService = new FileNoleggioService(NOLEGGI_FILE_NAME, INTERVENTI_ASSISTENZA_FILE_NAME,veicoloService,utenteService);
		prenotazioneService=new FilePrenotazioneService(PRENOTAZIONI_FILE_NAME,veicoloService,utenteService,NOTIFICHE_FILE_NAME);
	} 

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
	}

	@Override
	public VeicoloService getVeicoloService() {
		return veicoloService;
	}

	@Override
	public NoleggioService getNoleggioService() {
		return noleggioService;
	}

	@Override
	public PrenotazioneService getPrenotazioneService() {
		return prenotazioneService;
	}

}
