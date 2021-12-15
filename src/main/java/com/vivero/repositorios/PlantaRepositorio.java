package com.vivero.repositorios;


import com.vivero.entidades.Planta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlantaRepositorio extends JpaRepository <Planta, String>{

	@Query("select c from Planta c where c.nombre like :nombre "
			+ "and c.precio between :precioMinimo and :precioMaximo "
			+ "and c.tamanio like :tamanio "
			+ "and c.destacado =:destacado "
			+ "and c.codigo like :codigo "
			+ "and c.estilo like :estilo "
			+ "and c.ubicacion like :ubicacion "
			+ "and c.luz like :luz")
	public List<Planta> listarPlantas(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, Boolean destacado, String codigo, String estilo, String ubicacion,String luz);
	
	@Query("select c from Planta c where c.nombre like :nombre "
			+ "and c.precio between :precioMinimo and :precioMaximo "
			+ "and c.tamanio like :tamanio "
			+ "and c.codigo like :codigo "
			+ "and c.estilo like :estilo "
				+ "and c.ubicacion like :ubicacion "
			+ "and c.luz like :luz")
	public List<Planta> listarPlantasSinDestacado(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, String codigo, String estilo, String ubicacion,String luz);
}

