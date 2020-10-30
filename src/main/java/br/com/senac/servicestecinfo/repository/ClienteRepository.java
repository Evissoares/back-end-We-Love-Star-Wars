package br.com.senac.servicestecinfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.senac.servicestecinfo.model.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long>, JpaRepository<Cliente, Long> {
	
	@Query(value = "SELECT * FROM CLIENTES WHERE id =?", nativeQuery = true)
	Cliente findClientByID(long id);

	@Query(value = "SELECT * FROM CLIENTES WHERE nome like ?%", nativeQuery = true)
	List<Cliente> findClientByName(String nome);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM CLIENTES WHERE id=?", nativeQuery = true)
	void deleteClientByID(Long id);
	
}
