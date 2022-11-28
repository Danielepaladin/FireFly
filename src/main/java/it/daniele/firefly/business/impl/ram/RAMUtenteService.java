package it.univaq.disim.oop.firefly.business.impl.ram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.UtenteNotFoundException;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Amministratore;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;

public class RAMUtenteService implements UtenteService {
	private static Map<Integer, Utente> utenti = new HashMap<>();
	private static Map<Integer, Operatore> operatori = new HashMap<>();
	private static Map<Integer, Cliente> clienti = new HashMap<>();
	private static Integer contatoreOperatori = 1;
	private static Integer contatoreClienti = 1;
	private static Integer contatoreAmministratori = 1;
	private static Map<Integer, Amministratore> amministratori = new HashMap<>();
	static {
		initializeBase();
	}

	public RAMUtenteService() {
 
	} 

	public static void initializeBase() {
		Operatore mario = new Operatore();

		mario.setNome("Mario");
		mario.setCognome("Rossi");
		mario.setUsername("mario.rossi1967");
		mario.setPassword("marinella72");
		mario.setId(contatoreOperatori++);
		operatori.put(mario.getId(), mario);

		Operatore paolo = new Operatore();
		paolo.setNome("Paolo");
		paolo.setCognome("Bianchi");
		paolo.setUsername("PaoloBianchi");
		paolo.setPassword("marinella72");
		paolo.setId(contatoreOperatori++);
		operatori.put(paolo.getId(), paolo);

		Cliente marco = new Cliente();

		marco.setNome("Marco");
		marco.setCognome("Verdi");
		marco.setUsername("marco673");
		marco.setPassword("8JMdqpqmsEZ3DV");
		marco.setCodiceFiscale("MRCVRD75T02E523A");
		marco.setDataNascita(LocalDate.of(1975, 12, 02));
		marco.setId(contatoreClienti++);
		marco.setNumeroPatente("U1R108108P");
		marco.setScadenzaPatente(LocalDate.of(2023, 12, 1));
		clienti.put(marco.getId(), marco);

		Cliente piero = new Cliente();
		piero.setNome("Piero");
		piero.setCognome("Perinis");
		piero.setUsername("pierbacco");
		piero.setPassword("buhsgopj");
		piero.setCodiceFiscale("PRNPRI66H05A345Q");
		piero.setDataNascita(LocalDate.of(1966, 06, 05));
		piero.setNumeroPatente("A1B108108C");
		piero.setScadenzaPatente(LocalDate.of(2023, 06, 05));
		piero.setId(contatoreClienti++);
		clienti.put(piero.getId(), piero);

		Amministratore gianluca = new Amministratore();
		gianluca.setNome("Gianluca");
		gianluca.setCognome("Michelis");
		gianluca.setPassword("passwordSicura_?");
		gianluca.setUsername("gianPiero");
		gianluca.setId(contatoreAmministratori++);
		amministratori.put(gianluca.getId(), gianluca);
	}

	@Override
	public Utente authenticate(String username, String password) throws BusinessException {

		if ("AD".equalsIgnoreCase(username)) {
			Utente amministratore = new Amministratore();
			amministratore.setId(1);
			amministratore.setUsername(username);
			amministratore.setPassword(password);
			amministratore.setNome("Administrator");
			amministratore.setCognome("De Amministratoris");
			return amministratore;
		}

		if ("OP".equalsIgnoreCase(username)) {
			Utente operatore = new Operatore();
			operatore.setPassword(password);
			operatore.setNome("Operatorus");
			operatore.setCognome("Operatorum");
			operatore.setUsername(username); 
			return operatore;
		}
		if ("CL".equalsIgnoreCase(username)) {
			Utente cliente = new Cliente();
			cliente.setPassword(password);
			cliente.setNome("Cliente");
			cliente.setCognome("Dell'Autonoleggio");
			cliente.setUsername(username);
			return cliente;
		}
		List<Operatore> operatori = findAllOperatori();
		for (Operatore op : operatori) {
			if (op.getUsername().equalsIgnoreCase(username)) {
				op.setPassword(password);
				return op;
			}
		}
		List<Cliente> clienti = findAllClienti();
		for (Cliente cl : clienti) {
			if (cl.getUsername().equalsIgnoreCase(username) && cl.getPassword().equals(password)) {
				return cl;
			}
		}
		List<Amministratore> amministratori = findAllAmministratori();
		for (Amministratore ad : amministratori) {
			if (ad.getUsername().equalsIgnoreCase(username) && ad.getPassword().equals(password)) {
				return ad;
			}
		} 

		throw new UtenteNotFoundException();
	}

	@Override
	public List<Operatore> findAllOperatori() throws BusinessException {
		return new ArrayList<>(operatori.values());
	}

	@Override
	public Operatore findOperatoreById(int id) throws BusinessException {
		for (Operatore op : findAllOperatori()) {
			if (op.getId() == id) {
				return op;
			}
		}
		return null;
	}

	@Override
	public void createOperatore(Operatore operatore) throws BusinessException {
		operatore.setId(contatoreOperatori++);
		operatori.put(operatore.getId(), operatore);
	}

	@Override
	public void updateOperatore(Operatore operatore) throws BusinessException {
		for (Operatore op : findAllOperatori()) {
			if (operatore.getId() == op.getId()) {
				op = operatore;
			}
		}
	}

	@Override
	public void deleteOperatore(Operatore operatore) throws BusinessException {
		operatori.remove(operatore.getId());
	}

	@Override
	public List<Cliente> findAllClienti() throws BusinessException {
		return new ArrayList<>(clienti.values());
	}

	@Override
	public Cliente findClienteById(int id) throws BusinessException {
		for (Cliente cl : findAllClienti()) {
			if (cl.getId() == id)
				return cl;
		}
		return null;
	}

	@Override
	public void createCliente(Cliente cliente) throws BusinessException {
		cliente.setId(contatoreClienti++);
		clienti.put(cliente.getId(), cliente);

	}

	@Override
	public void updateCliente(Cliente cliente) throws BusinessException {
		for (Cliente cl : findAllClienti()) {
			if (cliente.getId() == cl.getId()) {
				cl = cliente;
			}
		}

	}

	@Override
	public void deleteCliente(Cliente cliente) throws BusinessException {
		clienti.remove(cliente.getId());

	}

	public List<Utente> findAllUtenti() throws BusinessException {
		int id = 0;
		for (Operatore op : findAllOperatori()) {
			utenti.put(id++, op);
		}
		for (Cliente cl : findAllClienti()) {
			utenti.put(id++, cl);
		}
		for(Amministratore ad:findAllAmministratori()) {
			utenti.put(id++, ad);
		}
		return new ArrayList<>(utenti.values());

	}

	@Override
	public List<Amministratore> findAllAmministratori() throws BusinessException {
		return new ArrayList<>(amministratori.values());
	}

	@Override
	public void createAmministratore(Amministratore amministratore) throws BusinessException {
		amministratore.setId(contatoreAmministratori++);
		amministratori.put(amministratore.getId(), amministratore);
	}

	@Override
	public void deleteAmministratore(Amministratore amministratore) throws BusinessException {
		amministratori.remove(amministratore.getId());
		
	}

	@Override
	public void updateAmministratore(Amministratore amministratore) throws BusinessException {
		for (Amministratore ad : findAllAmministratori()) {
			if (amministratore.getId() == ad.getId()) {
				ad = amministratore;
			}
		}
		
	}

}
