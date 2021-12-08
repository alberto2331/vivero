/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vivero.controladores;

import com.vivero.entidades.Accesorio;
import com.vivero.entidades.Foto;
import com.vivero.entidades.Maceta;
import com.vivero.entidades.Planta;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.AccesorioRepositorio;
import com.vivero.repositorios.FotoRepositorio;
import com.vivero.repositorios.MacetaRepositorio;
import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.FotoServicio;
import com.vivero.servicios.PlantaServicio;
import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private PlantaRepositorio plantaRepositorio;

        @Autowired
    private AccesorioRepositorio accesorioRepositorio;
        
    @Autowired
    private MacetaRepositorio macetaRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

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
    
    @GetMapping("/accesorio/{id}")
    public ResponseEntity<byte[]> getByAccesorio(@PathVariable String id) throws ErrorServicio {
        try {
            Accesorio accesorio = accesorioRepositorio.getById(id);

            if (accesorio.getPortada() == null) {
                throw new ErrorServicio("El producto no tiene una imagen.");
            }
            byte[] imagen = accesorio.getPortada().getContenido();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity(imagen, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorServicio("Hubo un problema al cargar la imagen del producto");
        }
    }
    
    @GetMapping("/maceta/{id}")
    public ResponseEntity<byte[]> getByMaceta(@PathVariable String id) throws ErrorServicio {
        try {
            Maceta maceta = macetaRepositorio.getById(id);

            if (maceta.getPortada() == null) {
                throw new ErrorServicio("El producto no tiene una imagen.");
            }
            byte[] imagen = maceta.getPortada().getContenido();

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity(imagen, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new ErrorServicio("Hubo un problema al cargar la imagen del producto");
        }
    }
    
    //Listar fotos de galeria
    @GetMapping("/galeria/{id}")
    public String getGaleriaByProducto(@PathVariable String id, Model model) throws ErrorServicio {
        try {
            System.out.println("Id: " + id);
            List<Foto> fotos = fotoServicio.listarFotosDeProducto(id);
            System.out.println("Fotos: " + fotos.toString());
            
            List<String> datosFotos = new ArrayList();
            
            for (Foto foto : fotos) {
                String base64 = Base64.encodeBase64String(foto.getContenido());
                datosFotos.add(base64);
            }
            
            model.addAttribute("listaFotos",datosFotos);
            
            return "planta-ver";
        } catch (Exception e) {
            throw new ErrorServicio("Hubo un problema al cargar las imagenes del producto");
        }
    }

}
