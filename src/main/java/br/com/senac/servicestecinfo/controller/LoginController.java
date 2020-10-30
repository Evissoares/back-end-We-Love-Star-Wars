package br.com.senac.servicestecinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.senac.servicestecinfo.dao.LoginDAO;
import br.com.senac.servicestecinfo.model.Cliente;
import br.com.senac.servicestecinfo.util.StringUtils;

@Component
public class LoginController {

	@Autowired
	LoginDAO dao;

	@Autowired
	StringUtils utils;

	public Cliente validarLogin(Cliente cliente) {
		Cliente novoLogin = new Cliente();

		try {
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			novoLogin = dao.encontrarClientePorLogin(cliente);
			if (novoLogin != null) {
				if (encode.matches(cliente.getSenha(), novoLogin.getSenha())) {
					return novoLogin;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return novoLogin;
	}

	public Cliente findById(long id) {
		Cliente clienteCompleto = null;

		try {
			clienteCompleto = dao.findById(id);
			if (clienteCompleto != null) {
				return clienteCompleto;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clienteCompleto;
	}

	public boolean delete(long id) {
		try {
			dao.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
