package it.univaq.disim.oop.firefly.business;

import it.univaq.disim.oop.firefly.business.impl.ram.RAMBusinessFactory;

public abstract class FireFlyBusinessFactory {
	//private static FireFlyBusinessFactory factory = new FileFireFlyBusinessFactoryImpl();
	 private static FireFlyBusinessFactory factory = new RAMBusinessFactory();

	public static FireFlyBusinessFactory getInstance() { 
		return factory;
	}

	public abstract UtenteService getUtenteService();
	public abstract PrenotazioneService getPrenotazioneService();
	public abstract VeicoloService getVeicoloService();

	public abstract NoleggioService getNoleggioService();

}
