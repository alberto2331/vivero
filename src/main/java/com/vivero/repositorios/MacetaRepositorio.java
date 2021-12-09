package com.vivero.repositorios;

import com.vivero.entidades.Maceta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MacetaRepositorio extends JpaRepository<Maceta, String> {

    @Query("select c from Maceta c where c.nombre like :nombre "
            + "and c.precio between :precioMinimo and :precioMaximo "
            + "and c.tamanio like :tamanio "
            + "and c.destacado =:destacado "
            + "and c.codigo like :codigo "
            + "and c.color like :color "
            + "and c.material like :material")
    public List<Maceta> listarMacetas(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, Boolean destacado, String codigo, String color, String material);

    @Query("select c from Maceta c where c.nombre like :nombre "
            + "and c.precio between :precioMinimo and :precioMaximo "
            + "and c.tamanio like :tamanio "
            + "and c.codigo like :codigo "
            + "and c.color like :color "
            + "and c.material like :material")

    public List<Maceta> listarMacetasSinDestacado(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, String codigo, String color, String material);

}
