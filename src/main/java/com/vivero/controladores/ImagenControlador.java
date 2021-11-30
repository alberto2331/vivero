/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivero.controladores;

import com.vivero.entidades.Planta;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.PlantaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    private PlantaRepositorio plantaRepositorio;
    
    @GetMapping("/planta/{id}")
    public ResponseEntity<byte[]> getByProducto(@PathVariable String id) throws ErrorServicio {
        try {
            Planta planta = plantaRepositorio.getById(id);
            
            if (planta.getPortada() == null) {
                throw new ErrorServicio("El producto no tiene una imagen.");
            }
            byte[] imagen = planta.getPortada().getContenido();
            
            HttpHeaders headers = new HttpHeaders();
            
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity(imagen, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorServicio("Hubo un problema al cargar la imagen del producto");
        }   
    }
    
}

