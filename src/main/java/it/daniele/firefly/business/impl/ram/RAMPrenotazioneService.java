package it.univaq.disim.oop.firefly.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.PrenotazioneService;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Notifica;
import it.univaq.disim.oop.firefly.domain.PrenotazioneNoleggio;
import it.univaq.disim.oop.firefly.domain.Preventivo;

public class RAMPrenotazioneService implements PrenotazioneService {
	private static Map<Integer, PrenotazioneNoleggio> prenotazioni = new HashMap<>();
	private static Integer contatorePrenotazioni = 2;
	private UtenteService utenteService;
	private VeicoloService veicoloService;

	public RAMPrenotazioneService(UtenteService utenteService, VeicoloService veicoloService) {
		this.utenteService = utenteService;
		this.veicoloService = veicoloService;
	}

	@Override
	public List<PrenotazioneNoleggio> findAllPrenotazioni() throws BusinessException {
		int id = 0;
		PrenotazioneNoleggio prenotazione = new PrenotazioneNoleggio();
		prenotazione.setCliente(utenteService.findClienteById(1));
		prenotazione.setVeicolo(veicoloService.findVeicoloById(1));
		prenotazione.setDataInizioNoleggio(LocalDate.of(2022, 04, 12));
		prenotazione.setDataFineNoleggio(LocalDate.of(2022, 05, 12));
		prenotazione.setId(id++);
		prenotazioni.put(prenotazione.getId(), prenotazione);
		PrenotazioneNoleggio prenotazione2 = new PrenotazioneNoleggio();
		prenotazione2.setCliente(utenteService.findClienteById(2));
		prenotazione2.setVeicolo(veicoloService.findVeicoloById(2));
		prenotazione2.setDataInizioNoleggio(LocalDate.of(2022, 05, 13));
		prenotazione2.setDataFineNoleggio(LocalDate.of(2022, 06, 13));
		prenotazione2.setId(id++);
		prenotazioni.put(prenotazione2.getId(), prenotazione2);
		return new ArrayList<>(prenotazioni.values());
	}

	@Override
	public void createPrenotazione(PrenotazioneNoleggio prenotazione) throws BusinessException {
		prenotazione.setId(contatorePrenotazioni++);
		prenotazioni.put(prenotazione.getId(), prenotazione);
	}

	@Override
	public List<Preventivo> findAllPreventivi() throws BusinessException {
		return null;
	}

	@Override
	public void createPreventivo() throws BusinessException {

	}

	@Override
	public void inviaNotifica(Notifica notifica) throws BusinessException {

	}

	@Override
	public List<Notifica> findAllNotifiche(Cliente cliente) throws BusinessException {
		return null;
	}

}
