package it.univaq.disim.oop.firefly.business;

import java.util.List;

import it.univaq.disim.oop.firefly.domain.Amministratore;
import it.univaq.disim.oop.firefly.domain.Cliente;
import it.univaq.disim.oop.firefly.domain.Operatore;
import it.univaq.disim.oop.firefly.domain.Utente;

public interface UtenteService {
	Utente authenticate(String username, String password) throws BusinessException;

	List<Amministratore> findAllAmministratori() throws BusinessException;

	void createAmministratore(Amministratore amministratore) throws BusinessException;

	void deleteAmministratore(Amministratore amministratore) throws BusinessException;

	void updateAmministratore(Amministratore amministratore) throws BusinessException;

	List<Operatore> findAllOperatori() throws BusinessException;

	Operatore findOperatoreById(int id) throws BusinessException;

	void createOperatore(Operatore operatore) throws BusinessException;

	void updateOperatore(Operatore operatore) throws BusinessException;

	void deleteOperatore(Operatore operatore) throws BusinessException;

	List<Cliente> findAllClienti() throws BusinessException;

	Cliente findClienteById(int id) throws BusinessException;

	void createCliente(Cliente cliente) throws BusinessException;

	void updateCliente(Cliente cliente) throws BusinessException;

	void deleteCliente(Cliente cliente) throws BusinessException;
}
