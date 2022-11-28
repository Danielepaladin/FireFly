package it.univaq.disim.oop.firefly.business;

import java.util.List;

import it.univaq.disim.oop.firefly.domain.Feedback;
import it.univaq.disim.oop.firefly.domain.TipologiaVeicolo;
import it.univaq.disim.oop.firefly.domain.Veicolo;

public interface VeicoloService {
	List<Veicolo> findAllVeicoli() throws BusinessException;
	Veicolo findVeicoloById(int id) throws BusinessException;
	void deleteVeicolo(Veicolo v) throws BusinessException;
	public void createVeicolo(Veicolo v) throws BusinessException;
	public void updateVeicolo(Veicolo v) throws BusinessException;
	
	List<TipologiaVeicolo> findAllTipologie() throws BusinessException;
	TipologiaVeicolo findTipologiaById(int id) throws BusinessException;
	public void createTipologiaVeicolo(TipologiaVeicolo tv) throws BusinessException;
	public void updateTipologiaVeicolo(TipologiaVeicolo tv) throws BusinessException;
	public void deleteTipologiaVeicolo(TipologiaVeicolo tipologia)throws BusinessException;
	public TipologiaVeicolo findTipologiaByNome(String nome)throws BusinessException;
	
	List<Feedback> findAllFeedBack(Veicolo v) throws BusinessException;
	public void deleteFeedback (Feedback f) throws BusinessException;//Solo il cliente che ha scritto il feedback lo può eliminare;
	public void updateFeedBack(Feedback f) throws BusinessException;//come sopra
	public void createFeedback(Feedback f) throws BusinessException;
	public List<Feedback> findAllFeedback() throws BusinessException;

	
	
	

}
