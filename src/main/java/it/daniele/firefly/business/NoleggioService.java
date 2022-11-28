package it.univaq.disim.oop.firefly.business;

import java.util.List;

import it.univaq.disim.oop.firefly.domain.InterventoAssistenza;
import it.univaq.disim.oop.firefly.domain.Noleggio;

public interface NoleggioService {
	List<Noleggio> findAllNoleggi()throws BusinessException;
	List<Noleggio> findNoleggiInCorso()throws BusinessException;
	Noleggio findNoleggioById(int id) throws BusinessException;
	
	List<InterventoAssistenza> findAllInterventiAssistenza() throws BusinessException;
	void createInterventoAssistenza(InterventoAssistenza intervento) throws BusinessException;
	void updateInterventoAssistenza(InterventoAssistenza intervento) throws BusinessException;
	

}
