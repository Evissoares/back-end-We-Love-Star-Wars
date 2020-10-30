package br.com.senac.servicestecinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.servicestecinfo.model.Cliente;

@Repository
public interface LoginRepository extends CrudRepository<Cliente, Long>, JpaRepository<Cliente, Long>{
	@Query(value = "SELECT * FROM CLIENTES WHERE email=?", nativeQuery = true)
	Cliente findByLogin(String email);
	
	@Query(value = "SELECT * FROM clientes WHERE id =?", nativeQuery = true)
	Cliente findById(long id);
}
