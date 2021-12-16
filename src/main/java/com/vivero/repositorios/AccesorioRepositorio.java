package com.vivero.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivero.entidades.Accesorio;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface AccesorioRepositorio extends JpaRepository<Accesorio, String> {

    @Query("select c from Accesorio c where c.nombre like :nombre "
            + "and c.precio between :precioMinimo and :precioMaximo "
            + "and c.tamanio like :tamanio "
            + "and c.destacado =:destacado "
            + "and c.codigo like :codigo "
            + "and c.categoria like :categoria ")
    public List<Accesorio> listarAccesorios(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, Boolean destacado, String codigo, String categoria);

    @Query("select c from Accesorio c where c.nombre like :nombre "
            + "and c.precio between :precioMinimo and :precioMaximo "
            + "and c.tamanio like :tamanio "
            + "and c.codigo like :codigo "
            + "and c.categoria like :categoria ")
    public List<Accesorio> listarAccesoriosSinDestacado(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, String codigo, String categoria);
    
    @Query("select c from Accesorio c where c.activo = :activo ")
    public List<Accesorio> listarAccActivos( Boolean activo);
}
