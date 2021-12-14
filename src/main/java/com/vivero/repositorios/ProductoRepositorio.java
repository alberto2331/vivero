package com.vivero.repositorios;


import com.vivero.entidades.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository <Producto, String>{
    

}