package com.vivero.controladores;

import com.vivero.errores.ErrorServicio;
import com.vivero.servicios.MacetaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/maceta")
public class MacetaControlador {

    @Autowired
    private MacetaServicio macetaServicio;

    @GetMapping("/maceta")
    public String registro(String color, String material, String nombre, Double precio, Integer stock,
                           String tamanio, MultipartFile archivo, String descripcion) throws ErrorServicio {
        return "maceta-creacion.html";
        /*Este GET tiene por finalidad navegar al form que permite realizar la carga de una planta
        TO DO:
	1) Acordar con la gente del front el nombre de la ruta desde el index al form que permite cargar macetas
	Por el momento la ruta es: "/maceta/formMaceta" 
         */
    }

    @PostMapping("/guardar")
    public String guardar(ModelMap modelo, 
    		@RequestParam String nombre,
    		@RequestParam Double precio,
    		@RequestParam Integer stock,
    		@RequestParam String tamanio,
    		@RequestParam MultipartFile archivo,    		
    		@RequestParam String descripcion,
    		@RequestParam String color,
    		@RequestParam String material 
    		) {
        try {
            macetaServicio.cargarMaceta(color, material, nombre, precio, stock, tamanio, archivo, descripcion);
            return "index";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "maceta-creacion";
        }
    }
}
