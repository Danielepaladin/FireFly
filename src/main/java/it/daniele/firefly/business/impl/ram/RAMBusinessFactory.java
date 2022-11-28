package it.univaq.disim.oop.firefly.business.impl.ram;

import it.univaq.disim.oop.firefly.business.FireFlyBusinessFactory;
import it.univaq.disim.oop.firefly.business.NoleggioService;
import it.univaq.disim.oop.firefly.business.PrenotazioneService;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.PrenotazioneNoleggio;

public class RAMBusinessFactory extends FireFlyBusinessFactory {
	private UtenteService utenteService;
	private VeicoloService veicoloService;
	private NoleggioService noleggioService;
	private PrenotazioneService prenotazioneService;

	public RAMBusinessFactory() {
		utenteService = new RAMUtenteService();
		veicoloService = new RAMVeicoloService(utenteService);
		noleggioService = new RAMNoleggioService(veicoloService, utenteService);
		prenotazioneService=new RAMPrenotazioneService(utenteService,veicoloService);
		

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
