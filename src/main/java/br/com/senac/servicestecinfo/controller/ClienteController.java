package br.com.senac.servicestecinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senac.servicestecinfo.dao.ClienteDAO;
import br.com.senac.servicestecinfo.model.Cliente;
import br.com.senac.servicestecinfo.util.StringUtils;

@Component
public class ClienteController {

	@Autowired
	ClienteDAO dao;
	
	@Autowired
	StringUtils utils;

	public ClienteController() {

	}
	
	public Cliente addClient(Cliente cliente) {
		try {
			if (utils.isEmail(cliente.getEmail())) {
				cliente.setSenha(utils.encrypthPass(cliente.getSenha()));
				cliente = dao.saveClient(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}

	public List<Cliente> findAllClient() {
		List<Cliente> listaClientes = null;
		try {
			listaClientes = dao.findAllClients();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaClientes;
	}
	
	public Cliente findClientByID(Cliente cliente) {
		try {
			cliente = dao.findClientByID(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	public List<Cliente> findClientByName(Cliente cliente) {
		List<Cliente> listaCliente= null;
		try {
			listaCliente = dao.findClientByName(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCliente;
	}
	
	public Cliente deleteClientByID(Cliente cliente) {
		try {
			cliente = dao.deleteClientByID(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}


}
