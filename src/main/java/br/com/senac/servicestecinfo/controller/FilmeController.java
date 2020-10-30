package br.com.senac.servicestecinfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senac.servicestecinfo.dao.FilmeDAO;
import br.com.senac.servicestecinfo.model.Filme;
import br.com.senac.servicestecinfo.util.StringUtils;

@Component
public class FilmeController {

	@Autowired
	FilmeDAO dao;

	@Autowired
	StringUtils utils;

	public Filme addFilme(Filme filme) {
		try {
				filme = dao.saveFilme(filme);
				
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return filme;
	}

	public List<Filme> findAllfilme() {
		List<Filme> listafilmes = null;
		try {
			listafilmes = dao.findAllFilmes();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listafilmes;
	}
	
	public Filme findfilmeById(Filme filme) {
		try {
			filme = dao.findFilmeById(filme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filme;
	}
	
	public List<Filme> findfilmeByTitulo(Filme filme) {
		List<Filme> listafilme= null;
		try {
			listafilme = dao.findFilmeByTitulo(filme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listafilme;
	}
	
	public Filme deletefilmeById(Filme filme) {
		try {
			filme = dao.deleteFilmeById(filme);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filme;
	}
}
