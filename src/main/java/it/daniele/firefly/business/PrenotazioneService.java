package it.univaq.disim.oop.firefly.business;

import java.util.List;

import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Notifica;
import it.univaq.disim.oop.firefly.domain.PrenotazioneNoleggio;
import it.univaq.disim.oop.firefly.domain.Preventivo;

public interface PrenotazioneService {
	List<PrenotazioneNoleggio> findAllPrenotazioni()throws BusinessException;
	void createPrenotazione(PrenotazioneNoleggio prenotazione) throws BusinessException;
	
	List<Preventivo> findAllPreventivi() throws BusinessException;
	void createPreventivo()throws BusinessException;
	
	void inviaNotifica(Notifica notifica) throws BusinessException;
	
	List<Notifica> findAllNotifiche(Cliente cliente) throws BusinessException;

}
