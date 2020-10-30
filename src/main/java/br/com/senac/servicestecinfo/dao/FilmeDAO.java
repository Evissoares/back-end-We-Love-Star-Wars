package br.com.senac.servicestecinfo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.senac.servicestecinfo.model.Filme;
import br.com.senac.servicestecinfo.repository.FilmeRepository;

@Component
public class FilmeDAO {

	@Autowired
	FilmeRepository repository;
	
	
	public Filme saveFilme(Filme filme) {
		try {
			filme = repository.save(filme);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filme;
	}

	public List<Filme> findAllFilmes() {
		List<Filme> listaFilmes = null;
		try {
			listaFilmes = repository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaFilmes;
	}
	
	public Filme findFilmeById(Filme filme) {
		try {
			filme = repository.findFilmeById(filme.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filme;
	}
	
	public List<Filme> findFilmeByTitulo(Filme filme) {
		List<Filme> listaFilme = null;
		try {
			listaFilme = repository.findFilmeByTitulo(filme.getTitulo().toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaFilme;
	}
	
 	public Filme deleteFilmeById(Filme filme) {
		try {
			
			filme = repository.deleteFilmeByID(filme.getId());
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		return filme;
	}
}
