package com.vivero.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivero.entidades.Planta;
import com.vivero.entidades.Producto;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.repositorios.ProductoRepositorio;

@Service
public class ProductoServicio {
	   
	@Autowired	
	private ProductoRepositorio productoRepositorio;
	
    public Producto buscarProducto(String id) throws ErrorServicio {
        Optional<Producto> Resp = productoRepositorio.findById(id);
        if (Resp.isPresent()) {
            Producto producto = Resp.get();
            return producto;
        } else {
            throw new ErrorServicio("Producto no encontrado");
        }
    }

}
