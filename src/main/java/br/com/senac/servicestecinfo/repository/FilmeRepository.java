package br.com.senac.servicestecinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.senac.servicestecinfo.model.Filme;

@Repository
public interface FilmeRepository extends CrudRepository<Filme, Long>, JpaRepository<Filme, Long> {

	
	@Query(value = "SELECT * FROM FILMES WHERE id =?", nativeQuery = true)
	Filme findFilmeById(long id);
	
	@Query(value = "SELECT * FROM FILMES WHERE TITULO like %?%", nativeQuery = true)
	List<Filme> findFilmeByTitulo(String titulo);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FILMES WHERE id=?", nativeQuery = true)
	Filme deleteFilmeByID(Long id);
}
