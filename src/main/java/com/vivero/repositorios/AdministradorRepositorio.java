package com.vivero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vivero.entidades.Admin;

@Repository
public interface AdministradorRepositorio extends JpaRepository<Admin, String>{

	@Query("select p from Admin p where p.dni = :dni")
	Admin findByDni(@Param("dni")String dni);
	
	@Query("select p from Admin p where p.username= :username")
	Admin findByUsername(@Param("username") String username);
}
