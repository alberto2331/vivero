package com.vivero.repositorios;




import com.vivero.entidades.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepositorio extends JpaRepository <Producto, String>{

    @Query("select c from Producto c where c.destacado =:destacado")
	public List<Producto> listarProductosDestacados(Boolean destacado);
	

}