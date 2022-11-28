package it.univaq.disim.oop.firefly.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.firefly.business.BusinessException;
import it.univaq.disim.oop.firefly.business.UtenteNotFoundException;
import it.univaq.disim.oop.firefly.business.UtenteService;
import it.univaq.disim.oop.firefly.domain.Amministratore;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;

public class FileUtenteService implements UtenteService {
	private String utentiFilename;
//	private String operatoriFileName;
	// private String clientiFileName;

	public FileUtenteService(String utentiFilename) {
		this.utentiFilename = utentiFilename;
		// this.operatoriFileName = operatoriFileName;
		// this.clientiFileName = clientiFileName;
	}

	@Override
	public Utente authenticate(String username, String password) throws UtenteNotFoundException, BusinessException {
		try {

			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[3].equals(username) && colonne[4].equals(password)) {
					Utente utente = null;
					switch (colonne[5]) {
					case "operatore":
						utente = new Operatore();
						break;
					case "amministratore":
						utente = new Amministratore();
						break;
					case "cliente":
						utente = new Cliente();
					default:
						break;
					}
					if (utente != null) {
						utente.setId(Integer.parseInt(colonne[0]));
						utente.setUsername(username);
						utente.setPassword(password);
						utente.setNome(colonne[1]);
						utente.setCognome(colonne[2]);
						if (utente instanceof Cliente) {
							((Cliente) utente).setCodiceFiscale(colonne[6]);
							((Cliente) utente).setDataNascita(LocalDate.parse(colonne[7]));
							((Cliente) utente).setNumeroPatente(colonne[8]);
							((Cliente) utente).setScadenzaPatente(LocalDate.parse(colonne[9]));
						}
					} else {
						throw new BusinessException("errore nella lettura del file");
					}

					return utente;
				}
			}
			throw new UtenteNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Operatore> findAllOperatori() throws BusinessException {
		List<Operatore> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				// id,nome,cogmome,username,password,tipoUtente
				if (colonne[5].equals("operatore")) {
					Operatore operatore = new Operatore();
					operatore.setId(Integer.parseInt(colonne[0]));
					operatore.setNome(colonne[1]);
					operatore.setCognome(colonne[2]);
					operatore.setUsername(colonne[3]);
					operatore.setPassword(colonne[4]);
					result.add(operatore);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public Operatore findOperatoreById(int id) throws BusinessException {
		Operatore result = new Operatore();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id && colonne[5].equals("operatore")) {
					result.setId(id);
					result.setNome(colonne[1]);
					result.setCognome(colonne[2]);
					result.setUsername(colonne[3]);
					result.setPassword(colonne[4]);

					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	@Override
	public void createOperatore(Operatore operatore) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				long contatore = fileData.getContatore();
				operatore.setId((int) contatore);
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(operatore.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(operatore.getCognome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(operatore.getUsername());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(operatore.getPassword());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append("operatore");
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	@Override
	public void updateOperatore(Operatore operatore) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == operatore.getId()) {
						StringBuilder row = new StringBuilder();

						row.append(operatore.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(operatore.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(operatore.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(operatore.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(operatore.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("operatore");
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}

	}

	@Override
	public void deleteOperatore(Operatore operatore) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore() + 1);
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == operatore.getId()) {
						righe[0] = "";
						righe[0].trim();
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public List<Cliente> findAllClienti() throws BusinessException {
		List<Cliente> result = new ArrayList<>();
		try {

			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if ("cliente".equals(colonne[5])) {
					Cliente cliente = new Cliente();
					cliente.setId(Integer.parseInt(colonne[0]));
					cliente.setNome(colonne[1]);
					cliente.setCognome(colonne[2]);
					cliente.setUsername(colonne[3]);
					cliente.setPassword(colonne[4]);
					cliente.setCodiceFiscale(colonne[6]);
					cliente.setDataNascita(LocalDate.parse(colonne[7]));
					cliente.setNumeroPatente(colonne[8]);
					cliente.setScadenzaPatente(LocalDate.parse(colonne[9]));
					result.add(cliente);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;

	}

	@Override
	public Cliente findClienteById(int id) throws BusinessException {
		Cliente result = new Cliente();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					result.setId(id);
					result.setNome(colonne[1]);
					result.setCognome(colonne[2]);
					result.setUsername(colonne[3]);
					result.setPassword(colonne[4]);
					result.setCodiceFiscale(colonne[5]);
					result.setDataNascita(LocalDate.parse(colonne[7]));
					result.setNumeroPatente(colonne[8]);
					result.setScadenzaPatente(LocalDate.parse(colonne[9]));
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		return result;
	}

	@Override
	public void createCliente(Cliente cliente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				// id,nome,cognome,username,password,tipoUtente,codFiscale,dataNascita,numeroPatente,scadenzaPatente
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getCognome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getUsername());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getPassword());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append("cliente");
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getCodiceFiscale());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getDataNascita());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getNumeroPatente());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cliente.getScadenzaPatente());
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	@Override
	public void updateCliente(Cliente cliente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == cliente.getId()) {
						StringBuilder row = new StringBuilder();
						// id,nome,cognome,username,password,tipoUtente,codFiscale,dataNascita,numeroPatente,scadenzaPatente
						row.append(cliente.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("cliente");
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getCodiceFiscale());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getDataNascita().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getNumeroPatente());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getScadenzaPatente().toString());

						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}

	}

	@Override
	public void deleteCliente(Cliente cliente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore() + 1);
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == cliente.getId()) {
						righe[0] = "";
						righe[0].trim();
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public List<Amministratore> findAllAmministratori() throws BusinessException {
		List<Amministratore> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				// id,nome,cogmome,username,password,tipoUtente
				if (colonne[5].equals("amministratore") && Integer.parseInt(colonne[0]) != 1) {
					Amministratore amministratore = new Amministratore();
					amministratore.setId(Integer.parseInt(colonne[0]));
					amministratore.setNome(colonne[1]);
					amministratore.setCognome(colonne[2]);
					amministratore.setUsername(colonne[3]);
					amministratore.setPassword(colonne[4]);
					result.add(amministratore);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public void createAmministratore(Amministratore amministratore) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				long contatore = fileData.getContatore();
				amministratore.setId((int) contatore);
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(amministratore.getNome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(amministratore.getCognome());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(amministratore.getUsername());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(amministratore.getPassword());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append("amministratore");
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	@Override
	public void deleteAmministratore(Amministratore amministratore) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore() + 1);
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == amministratore.getId()) {
						righe[0] = "";
						righe[0].trim();
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	@Override
	public void updateAmministratore(Amministratore amministratore) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			try (PrintWriter writer = new PrintWriter(new File(utentiFilename))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {
					if (Long.parseLong(righe[0]) == amministratore.getId()) {
						StringBuilder row = new StringBuilder();
						// id,nome,cognome,username,password,tipoUtente,codFiscale,dataNascita,numeroPatente,scadenzaPatente
						row.append(amministratore.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(amministratore.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(amministratore.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(amministratore.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(amministratore.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("amministratore");
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException();
		}
		
	}

}
