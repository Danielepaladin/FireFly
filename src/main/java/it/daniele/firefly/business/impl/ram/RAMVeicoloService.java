package it.univaq.disim.oop.firefly.business.impl.ram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.business.VeicoloService;
import it.univaq.disim.oop.firefly.domain.Alimentazione;
import it.univaq.disim.oop.firefly.domain.CampoDiUtilizzo;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Feedback;
import it.univaq.disim.oop.firefly.domain.StatoVeicolo;
import it.univaq.disim.oop.firefly.domain.TipologiaVeicolo;
import it.univaq.disim.oop.firefly.domain.Valutazione;
import it.univaq.disim.oop.firefly.domain.Veicolo;

public class RAMVeicoloService implements VeicoloService {
	private static List<Veicolo> base = new ArrayList<>();
	private static List<Veicolo> aggiunti = new ArrayList<>();
	private static List<TipologiaVeicolo> tipologieBase = new ArrayList<>();
	private static List<TipologiaVeicolo> tipologieAggiunte = new ArrayList<>();
	private static Map<Integer, Feedback> feedback = new HashMap<>();;
	private static Integer contatoreTipologie = 0;
	private static Integer contatoreId = 0;
	private static Integer contatoreFeed = 0;
	private UtenteService utenteService;

	public RAMVeicoloService(UtenteService utenteService) {
		this.utenteService = utenteService;

	}

	static {
		initializeBase();
	}

	public static void initializeBase() {

		TipologiaVeicolo utilitaria = new TipologiaVeicolo();
		utilitaria.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOPERSONE);
		List<String> carataristiche = new ArrayList<>();
		carataristiche.add("Questo");
		carataristiche.add("Quello");
		carataristiche.add("Tutto");
		utilitaria.setCaratteristiche(carataristiche);
		utilitaria.setId(contatoreTipologie++);
		utilitaria.setNomeTipologia("Utilitaria");
		tipologieBase.add(utilitaria);

		TipologiaVeicolo berlina = new TipologiaVeicolo();
		berlina.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOPERSONE);
		berlina.setCaratteristiche(carataristiche);
		berlina.setId(contatoreTipologie++);
		berlina.setNomeTipologia("Berlina");
		tipologieBase.add(berlina);

		TipologiaVeicolo furgone = new TipologiaVeicolo();
		furgone.setCampoDiUtilizzo(CampoDiUtilizzo.TRASPORTOMERCI);
		carataristiche = new ArrayList<>();
		carataristiche.add("Grandi");
		carataristiche.add("Belli");
		carataristiche.add("eccetera");
		furgone.setCaratteristiche(carataristiche);
		furgone.setId(contatoreTipologie++);
		furgone.setNomeTipologia("Furgone");
		tipologieBase.add(furgone);

		Veicolo panda = new Veicolo();
		panda.setId(contatoreId++);
		panda.setTarga("CT775AC");
		panda.setNome("Panda");
		panda.setCasaProduttrice("Fiat");
		panda.setAnnoImmatricolazione(1989);
		panda.setDescrizione("BLALALALBBLALBALBALBALBALBALBAlBA");
		panda.setAlimentazione(Alimentazione.BENZINA);
		panda.setChilometraggio(200000);
		panda.setTariffaBreveTermine(1.76);
		panda.setTariffaMedioTermine(1.45);
		panda.setTariffaLungoTermine(1.00);
		panda.setStato(StatoVeicolo.MANUTENZIONE);
		panda.setTipologia(utilitaria);

		base.add(panda);

		Veicolo punto = new Veicolo();
		punto.setId(contatoreId++);
		punto.setTarga("CT775AD");
		punto.setNome("Punto");
		punto.setCasaProduttrice("Fiat");
		punto.setAnnoImmatricolazione(2012);
		punto.setDescrizione("BLALALALBBLALBALBALBALBALBALBAlBA");
		punto.setAlimentazione(Alimentazione.DIESEL);
		punto.setChilometraggio(100000);
		punto.setTariffaBreveTermine(1.86);
		punto.setTariffaMedioTermine(1.75);
		punto.setTariffaLungoTermine(1.50);
		punto.setStato(StatoVeicolo.DISPONIBILE);
		punto.setTipologia(utilitaria);

		base.add(punto);
		Veicolo golf = new Veicolo();
		golf.setId(contatoreId++);
		golf.setTarga("CT275AD");
		golf.setNome("Golf");
		golf.setCasaProduttrice("Volkswagen");
		golf.setAnnoImmatricolazione(2012);
		golf.setDescrizione("BLALALALBBLALBALBALBALBALBALBAlBA");
		golf.setAlimentazione(Alimentazione.DIESEL);
		golf.setChilometraggio(100000);
		golf.setTariffaBreveTermine(1.86);
		golf.setTariffaMedioTermine(1.75);
		golf.setTariffaLungoTermine(1.50);
		golf.setStato(StatoVeicolo.DISPONIBILE);
		golf.setTipologia(berlina);

		base.add(golf);

		Veicolo y = new Veicolo();
		y.setId(contatoreId++);
		y.setTarga("CT775AD");
		y.setNome("Ypsilon");
		y.setCasaProduttrice("Lancia");
		y.setAnnoImmatricolazione(2012);
		y.setDescrizione("BLALALALBBLALBALBALBALBALBALBAlBA");
		y.setAlimentazione(Alimentazione.DIESEL);
		y.setChilometraggio(100000);
		y.setTariffaBreveTermine(1.86);
		y.setTariffaMedioTermine(1.75);
		y.setTariffaLungoTermine(1.50);
		y.setStato(StatoVeicolo.NOLEGGIO);
		y.setTipologia(utilitaria);

		base.add(y);

	}

	@Override
	public List<Veicolo> findAllVeicoli() throws BusinessException {
		List<Veicolo> temp = new ArrayList<>();
		temp.addAll(base);
		temp.addAll(aggiunti);
		return temp;
	}

	@Override
	public Veicolo findVeicoloById(int id) throws BusinessException {
		List<Veicolo> temp = new ArrayList<>();
		temp = findAllVeicoli();
		for (Veicolo vl : temp) {
			if (vl.getId() == id)
				return vl;
		}
		return null;
	}

	@Override
	public void deleteVeicolo(Veicolo veicolo) throws BusinessException {
		for (Iterator<Veicolo> iterator = base.iterator(); iterator.hasNext();) {
			Veicolo value = iterator.next();
			if (value.getId().equals(veicolo.getId()))
				iterator.remove();
		}
		for (Iterator<Veicolo> iterator = aggiunti.iterator(); iterator.hasNext();) {
			Veicolo value = iterator.next();
			if (value.getId().equals(veicolo.getId()))
				iterator.remove();
		}

	}

	@Override
	public void createVeicolo(Veicolo veicolo) throws BusinessException {
		veicolo.setId(contatoreId++);
		aggiunti.add(veicolo);
	}

	@Override
	public void updateVeicolo(Veicolo veicolo) throws BusinessException {
		List<Veicolo> temp = new ArrayList<>();
		temp = findAllVeicoli();
		for (Veicolo vl : temp) {
			if (veicolo.getId() == vl.getId()) {
				vl.setAlimentazione(veicolo.getAlimentazione());
				vl.setAnnoImmatricolazione(veicolo.getAnnoImmatricolazione());
				vl.setChilometraggio(veicolo.getChilometraggio());
				vl.setDescrizione(veicolo.getDescrizione());
				vl.setTarga(veicolo.getTarga());
				vl.setTipologia(veicolo.getTipologia());
				vl.setTariffaBreveTermine(veicolo.getTariffaBreveTermine());
				vl.setTariffaMedioTermine(veicolo.getTariffaMedioTermine());
				vl.setTariffaLungoTermine(veicolo.getTariffaLungoTermine());
				vl.setStato(veicolo.getStato());
			}
		}
	}
	// Tipologia veicolo gestita in VeicoloService

	@Override
	public List<TipologiaVeicolo> findAllTipologie() throws BusinessException {
		List<TipologiaVeicolo> temp = new ArrayList<>();
		temp.addAll(tipologieBase);
		temp.addAll(tipologieAggiunte);
		return temp;
	}

	@Override
	public TipologiaVeicolo findTipologiaById(int id) throws BusinessException {
		List<TipologiaVeicolo> temp = new ArrayList<>();
		temp = findAllTipologie();
		for (TipologiaVeicolo tv : temp) {
			if (tv.getId() == id) {
				return tv;
			}

		}
		return null;
	}

	@Override
	public void createTipologiaVeicolo(TipologiaVeicolo tipologiaVeicolo) throws BusinessException {
		tipologiaVeicolo.setId(contatoreTipologie++);
		tipologieAggiunte.add(tipologiaVeicolo);
	}

	@Override
	public void updateTipologiaVeicolo(TipologiaVeicolo tipologiaVeicolo) throws BusinessException {
		List<TipologiaVeicolo> temp = new ArrayList<>();
		temp = findAllTipologie();
		for (TipologiaVeicolo tv : temp) {
			if (tipologiaVeicolo.getId() == tv.getId()) {
				tv.setCampoDiUtilizzo(tipologiaVeicolo.getCampoDiUtilizzo());
				tv.setCaratteristiche(tv.getCaratteristiche());
				tv.setNomeTipologia(tipologiaVeicolo.getNomeTipologia());
			}
		}
	}

	@Override
	public TipologiaVeicolo findTipologiaByNome(String nome) throws BusinessException {
		List<TipologiaVeicolo> temp = new ArrayList<>();
		temp = findAllTipologie();
		for (TipologiaVeicolo tv : temp) {
			if (tv.getNomeTipologia() == nome) {
				return tv;
			}

		}
		return null;
	}

	@Override
	public void deleteTipologiaVeicolo(TipologiaVeicolo tipologia) {
		for (Iterator<TipologiaVeicolo> iterator = tipologieBase.iterator(); iterator.hasNext();) {
			TipologiaVeicolo value = iterator.next();
			if (value.getId().equals(tipologia.getId()))
				iterator.remove();
		}
		for (Iterator<TipologiaVeicolo> iterator = tipologieAggiunte.iterator(); iterator.hasNext();) {
			TipologiaVeicolo value = iterator.next();
			if (value.getId().equals(tipologia.getId()))
				iterator.remove();
		}

	}

	@Override
	public List<Feedback> findAllFeedBack(Veicolo v) throws BusinessException {

		List<Feedback> result = new ArrayList<>();
		for (Feedback feed : feedback.values()) {
			if (feed.getVeicolo().getId() == v.getId()) {
				result.add(feed);
			}

		}
		return result;
	}

//feedBack o feedback 
	@Override
	public void deleteFeedback(Feedback feedback) throws BusinessException {
		for (Iterator<TipologiaVeicolo> iterator = tipologieBase.iterator(); iterator.hasNext();) {
			TipologiaVeicolo value = iterator.next();
			if (value.getId().equals(feedback.getId()))
				iterator.remove();
		}
		for (Iterator<TipologiaVeicolo> iterator = tipologieAggiunte.iterator(); iterator.hasNext();) {
			TipologiaVeicolo value = iterator.next();
			if (value.getId().equals(feedback.getId()))
				iterator.remove();
		}

	}

	@Override
	public void updateFeedBack(Feedback feedback) throws BusinessException {
		List<Feedback> temp = new ArrayList<>();
		temp = findAllFeedBack(feedback.getVeicolo());
		for (Feedback feed : temp) {
			if (feedback.getId() == feed.getId()) {
				feed.setRilasciatoDa(feedback.getRilasciatoDa());
				feed.setTesto(feedback.getTesto());
				;
				feed.setValutazione(feedback.getValutazione());
				feed.setVeicolo(feedback.getVeicolo());
				;

			}
		}
	}

	@Override
	public void createFeedback(Feedback feedback) throws BusinessException {
		feedback.setId(contatoreFeed++);
		this.feedback.put(feedback.getId(), feedback);
	}

	@Override
	public List<Feedback> findAllFeedback() throws BusinessException {
		Feedback pandaFeedbackUno = new Feedback();
		Cliente cliente = utenteService.findClienteById(1);
		int id = 1;
		pandaFeedbackUno.setId(id++);
		pandaFeedbackUno.setRilasciatoDa(cliente);
		pandaFeedbackUno.setVeicolo(findVeicoloById(0));
		pandaFeedbackUno.setValutazione(Valutazione.UNO);
		pandaFeedbackUno.setTesto("ok");
		feedback.put(pandaFeedbackUno.getId(), pandaFeedbackUno);

		Feedback pandaFeedbackDue = new Feedback();
		Cliente clienteDue = utenteService.findClienteById(2);

		pandaFeedbackDue.setId(id++);
		pandaFeedbackDue.setRilasciatoDa(clienteDue);
		pandaFeedbackDue.setVeicolo(findVeicoloById(0));
		pandaFeedbackDue.setValutazione(Valutazione.CINQUE);
		pandaFeedbackDue.setTesto("ok");
		feedback.put(pandaFeedbackDue.getId(), pandaFeedbackDue);
		return new ArrayList<>(feedback.values());

	}

}
