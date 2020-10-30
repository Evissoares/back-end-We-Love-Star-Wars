package br.com.senac.servicestecinfo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senac.servicestecinfo.model.Cliente;
import br.com.senac.servicestecinfo.repository.ClienteRepository;

@Component
public class ClienteDAO {
	
	@Autowired
	ClienteRepository repository;
	
	
	public Cliente saveClient(Cliente cliente) {
		try {
			cliente = repository.save(cliente);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}

	public List<Cliente> findAllClients() {
		List<Cliente> listaClientes = null;
		try {
			listaClientes = repository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaClientes;
	}
	
	public Cliente findClientByID(Cliente cliente) {
		try {
			cliente = repository.findClientByID(cliente.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	public List<Cliente> findClientByName(Cliente cliente) {
		List<Cliente> listaCliente = null;
		try {
			listaCliente = repository.findClientByName(cliente.getNome());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCliente;
	}
	
 	public Cliente deleteClientByID(Cliente cliente) {
		try {
			cliente = repository.findClientByID(cliente.getId());
			repository.deleteClientByID(cliente.getId());
			} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}


}
