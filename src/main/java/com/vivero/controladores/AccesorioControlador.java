package com.vivero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vivero.errores.ErrorServicio;
import com.vivero.servicios.AccesorioServicio;

@Controller
@RequestMapping("/accesorio")
public class AccesorioControlador {
	
	@Autowired
    private AccesorioServicio accesorioServicio;
	
    @GetMapping("/accesorio")
    public String registro() {
        return "accesorio-creacion.html";
    }

    @PostMapping("/guardar")
    public String guardar(ModelMap modelo, 
    		@RequestParam String nombre,
    		@RequestParam Double precio,
    		@RequestParam Integer stock,
    		@RequestParam String tamanio,    		
    		@RequestParam String descripcion,
    		@RequestParam String categoria,
    		@RequestParam MultipartFile portada,
    		@RequestParam MultipartFile[] imagenes    		
    		){    	
    		try {
				accesorioServicio.cargarAccesorio(nombre, precio, stock, tamanio, descripcion,categoria, portada,imagenes);
				return "index";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				modelo.put("error", e.getMessage());
	            return "accesorio-creacion";
			}
                           
    }

}
