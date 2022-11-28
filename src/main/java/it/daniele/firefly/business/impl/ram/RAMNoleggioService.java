package it.univaq.disim.oop.firefly.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.NoleggioService;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.InterventoAssistenza;
import it.univaq.disim.oop.firefly.domain.Noleggio;
import it.univaq.disim.oop.firefly.domain.TipoRichiesta;
import it.univaq.disim.oop.firefly.domain.Veicolo;

public class RAMNoleggioService implements NoleggioService {
	private VeicoloService veicoloService;
	private UtenteService utenteService;
	private static Map<Integer, Noleggio> noleggi=new HashMap<>();
	private  static Map<Integer, InterventoAssistenza> interventi=new HashMap<>();

	
	public RAMNoleggioService(VeicoloService veicoloService, UtenteService utenteService) {
		this.utenteService = utenteService;
		this.veicoloService = veicoloService;

	}

	@Override
	public List<Noleggio> findAllNoleggi() throws BusinessException {
		 Integer idNoleggi=0;
		Cliente mario = utenteService.findClienteById(1);// marco
		Veicolo panda = veicoloService.findVeicoloById(1);
		Cliente paolo = utenteService.findClienteById(2);// piero 

		Noleggio noleggio1 = new Noleggio();
		noleggio1.setUtente(paolo);
		noleggio1.setDataInizio(LocalDate.now().minusDays(30));
		noleggio1.setDataFine(LocalDate.now().minusDays(20));
		noleggio1.setId(idNoleggi++);
		noleggio1.setVeicolo(panda);
		noleggi.put(noleggio1.getId(), noleggio1);

		Noleggio noleggio2 = new Noleggio();
		noleggio2.setUtente(mario);
		noleggio2.setVeicolo(panda);
		noleggio2.setDataInizio(LocalDate.now().minusDays(1));// ieri
		noleggio2.setDataFine(LocalDate.now().plusDays(10));
		noleggio2.setId(idNoleggi++);
		noleggi.put(noleggio2.getId(), noleggio2);

		Noleggio noleggio3 = new Noleggio();
		noleggio3.setUtente(paolo);
		noleggio3.setDataInizio(LocalDate.now().minusDays(3));
		noleggio3.setDataFine(LocalDate.now().plusDays(12));
		noleggio3.setId(idNoleggi++);
		noleggio3.setVeicolo(veicoloService.findVeicoloById(2));
		noleggi.put(noleggio3.getId(), noleggio3);

		return new ArrayList<>(noleggi.values());
	} 

	@Override
	public List<Noleggio> findNoleggiInCorso() throws BusinessException {
		List<Noleggio> result = new ArrayList<>();
		for (Noleggio nol : findAllNoleggi()) {
			if (LocalDate.now().isAfter(nol.getDataInizio()) && LocalDate.now().isBefore(nol.getDataFine())) {
				result.add(nol);
			}
		}
		return result;
	}

	@Override
	public Noleggio findNoleggioById(int id) throws BusinessException {
	return	noleggi.get(id);
	}

	@Override
	public List<InterventoAssistenza> findAllInterventiAssistenza() throws BusinessException {
		Integer idInterventi=0;
		InterventoAssistenza intervento1=new InterventoAssistenza();
		intervento1.setCompletato(false);
		intervento1.setVeicoloSostitutivo(false);
		intervento1.setDataRichiesta(LocalDate.now());
		intervento1.setTipoRichesta(TipoRichiesta.GUASTO);;
		intervento1.setDescrizione("BLABLALBALBLABALA");
		intervento1.setNoleggio(findNoleggioById(1));
		intervento1.setVeicolo(veicoloService.findVeicoloById(2));
		intervento1.setId(idInterventi++);
		intervento1.setCliente(utenteService.findClienteById(1));
		interventi.put(intervento1.getId(),intervento1);
		
		InterventoAssistenza intervento2=new InterventoAssistenza();
		intervento2.setCompletato(true);
		intervento2.setVeicoloSostitutivo(true);;
		intervento2.setDataRichiesta(LocalDate.now().minusDays(5));
		intervento2.setDataCompletamento(LocalDate.now().minusDays(2));
		intervento2.setTipoRichesta(TipoRichiesta.INCIDENTE);;
		intervento2.setDescrizione("BLABLALBALBLABALA");
		intervento2.setNoleggio(findNoleggioById(2));
		intervento2.setVeicolo(veicoloService.findVeicoloById(1));
		intervento2.setId(idInterventi++);
		intervento2.setCliente(utenteService.findClienteById(1));
		interventi.put(intervento2.getId(),intervento2);
		
		
		return new ArrayList<>(interventi.values());

	}

	@Override
	public void createInterventoAssistenza(InterventoAssistenza intervento) throws BusinessException {
		intervento.setId(interventi.size());
		interventi.put(intervento.getId(), intervento);

	}

	@Override
	public void updateInterventoAssistenza(InterventoAssistenza intervento) throws BusinessException {
		for(InterventoAssistenza interv:findAllInterventiAssistenza()) {
			if(interv.getId()==intervento.getId()) {
				interv=intervento;
			}
		}

	}

}
