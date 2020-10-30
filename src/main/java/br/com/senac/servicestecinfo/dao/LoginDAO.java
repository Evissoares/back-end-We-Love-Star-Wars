package br.com.senac.servicestecinfo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senac.servicestecinfo.model.Cliente;
import br.com.senac.servicestecinfo.repository.LoginRepository;

@Component
public class LoginDAO {
	@Autowired
	LoginRepository repository;

	public Cliente encontrarClientePorLogin(Cliente cliente) {
		Cliente clienteValido = new Cliente();

		try {
			System.out.println(cliente.getEmail());
			clienteValido = repository.findByLogin(cliente.getEmail());
			// System.out.println(clienteValido.getEmail() + clienteValido.getSenha());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clienteValido;
	}
	
	

	public Cliente findById(long id) {
		Cliente clienteCompleto = null;
		try {
			clienteCompleto = repository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clienteCompleto;
	}

	public boolean delete(long id) {
		boolean remove = false;
		try {
			repository.deleteById(id);
			remove = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return remove;
	}

}
