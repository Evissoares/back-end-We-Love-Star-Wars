package br.com.senac.servicestecinfo.model;

import java.sql.Blob;
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
@Table(name = "filme")
@Getter @Setter
public class Filme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "personagens")
	private String personagens;
	
	@Column(name = "imagem", nullable = true)
	private Blob imagem;

	@Column(name = "veiculos", nullable = false)
	@ManyToMany
	private List<Veiculo> listaVeiculo;
}
