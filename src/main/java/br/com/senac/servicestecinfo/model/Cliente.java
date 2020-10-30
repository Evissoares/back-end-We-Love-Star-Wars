package br.com.senac.servicestecinfo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter@Setter
public class Cliente {
	
	public Cliente() {}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "filmes", nullable = true)
	@ManyToMany
    private List<Filme> listaFilmes;
}
