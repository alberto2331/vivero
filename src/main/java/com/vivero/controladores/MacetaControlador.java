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
    public String registro() {
        return "maceta-creacion.html";        
    }

    @PostMapping("/guardar")
    public String guardar(ModelMap modelo, 
    		@RequestParam String nombre,
    		@RequestParam Double precio,
    		@RequestParam Integer stock,
    		@RequestParam String tamanio,    		
    		@RequestParam String descripcion,
    		@RequestParam String color,
    		@RequestParam String material,
    		@RequestParam MultipartFile portada,
    		@RequestParam MultipartFile[] imagenes
    		) {
        try {
            macetaServicio.cargarMaceta(color, material, nombre, precio, stock, tamanio, descripcion, portada, imagenes);
            return "index";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            return "maceta-creacion";
        }
    }
}
