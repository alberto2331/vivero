package com.vivero.controladores;

import com.vivero.entidades.Foto;
/*Importado momentaneamente para poder mostrar imagenes de productos a modificar
Entiendo que cuando juntemos las ramas de front y back podremos borrar esta importacion y usar el fotoServicio de Juan Guardiola 
 */
import com.vivero.entidades.Planta;
import com.vivero.entidades.Portada;
import com.vivero.entidades.Producto;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.FotoRepositorio;/*Importado momentaneamente para poder mostrar imagenes de productos a modificar
Entiendo que cuando juntemos las ramas de front y back podremos borrar esta importacion y usar el fotoServicio de Juan Guardiola
 */
import com.vivero.repositorios.ProductoRepositorio;
//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
//import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.PlantaServicio;
import com.vivero.servicios.ProductoServicio;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.json.*;
@Controller
@RequestMapping("/")
public class IndexControlador {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @GetMapping("/")
    public String plantaDestacada( ModelMap modelo) {
                 
            Boolean destacado;
            destacado = true;
          
            List<Producto> plantasFiltradas = productoRepositorio.listarProductosDestacados(destacado);
            modelo.put("plantasFiltradas", plantasFiltradas);
            return "index";
        }
        
    }
    
    