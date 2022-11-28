package it.univaq.disim.oop.firefly.business.impl.file;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.NoleggioService;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.InterventoAssistenza;
import it.univaq.disim.oop.firefly.domain.Noleggio;
import it.univaq.disim.oop.firefly.domain.TipoRichiesta;
import it.univaq.disim.oop.firefly.domain.Veicolo;

public class FileNoleggioService implements NoleggioService {
	private String noleggiFileName;
	private VeicoloService veicoloService;
	private UtenteService utenteService;
	private String interventiAssistenzaFileName;

	public FileNoleggioService(String noleggiFileName,String interventiAssistenzaFileName, VeicoloService veicoloService, UtenteService utenteService) {
		this.veicoloService = veicoloService;
		this.noleggiFileName = noleggiFileName;
		this.utenteService=utenteService;
		this.interventiAssistenzaFileName=interventiAssistenzaFileName;
	}

	@Override
	public List<Noleggio> findAllNoleggi() throws BusinessException {
		// id,idveicolo,idutente,dataInizio,inCorso,dataFine
		List<Noleggio> result = new ArrayList<>(); 
		try {
			FileData fileData = Utility.readAllRows(noleggiFileName);
			for (String[] colonne : fileData.getRighe()) {
				Noleggio noleggio = new Noleggio();
				noleggio.setId(Integer.parseInt(colonne[0]));
				Veicolo veicolo = veicoloService.findVeicoloById(Integer.parseInt(colonne[1]));
				Cliente cliente = utenteService.findClienteById(Integer.parseInt(colonne[2]));
				noleggio.setUtente(cliente);
				noleggio.setVeicolo(veicolo);
				noleggio.setDataInizio(LocalDate.parse(colonne[3]));
				noleggio.setInCorso(Boolean.valueOf(colonne[4]));
				noleggio.setDataFine(LocalDate.parse(colonne[5]));
				result.add(noleggio);
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;

	}

	@Override
	public List<Noleggio> findNoleggiInCorso() throws BusinessException {
		List<Noleggio> result= new ArrayList<>();
		for(Noleggio noleggio:findAllNoleggi()) {
			if (noleggio.getDataInizio().isBefore(LocalDate.now())&&noleggio.getDataFine().isAfter(LocalDate.now())) {
				result.add(noleggio);
			}
		}
		return result;
	}

	@Override
	public Noleggio findNoleggioById(int id) throws BusinessException {
		Noleggio result = new Noleggio();
		try {
			FileData fileData = Utility.readAllRows(noleggiFileName);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					result.setId(id);
					result.setVeicolo(veicoloService.findVeicoloById(Integer.parseInt(colonne[1])));
					result.setUtente(utenteService.findClienteById(Integer.parseInt(colonne[2])));
					result.setDataInizio(LocalDate.parse(colonne[3]));
					result.setInCorso(Boolean.valueOf(colonne[4]));
					result.setDataFine(LocalDate.parse(colonne[5]));
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		return result;
	}

	@Override
	public List<InterventoAssistenza> findAllInterventiAssistenza() throws BusinessException {
		List<InterventoAssistenza> result = new ArrayList<>(); 
		try {
			FileData fileData = Utility.readAllRows(interventiAssistenzaFileName);
			for (String[] colonne : fileData.getRighe()) {
				InterventoAssistenza intervento = new InterventoAssistenza();
				intervento.setId(Integer.parseInt(colonne[0]));
				intervento.setCliente(utenteService.findClienteById(Integer.parseInt(colonne[1])));
				intervento.setVeicolo( veicoloService.findVeicoloById(Integer.parseInt(colonne[2])));
				intervento.setDataRichiesta(LocalDate.parse(colonne[3]));
				intervento.setCompletato(Boolean.valueOf(colonne[4]));
				if (intervento.getCompletato()) {
				intervento.setDataCompletamento(LocalDate.parse(colonne[5]));
				}
				intervento.setNoleggio(findNoleggioById(Integer.parseInt(colonne[6])));
				intervento.setVeicoloSostitutivo(Boolean.valueOf(colonne[7]));
				intervento.setTipoRichesta(TipoRichiesta.valueOf(colonne[8]));
				intervento.setDescrizione(colonne[9]);
				result.add(intervento);
			}//id,clienteid,veicoloid,datar,completato?,datac,noleggioid,veicolosostitutivo?,tiporic,desc

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;

	}

	@Override
	public void createInterventoAssistenza(InterventoAssistenza intervento) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInterventoAssistenza(InterventoAssistenza intervento) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

}
