package com.vivero.controladores;

//import com.vivero.entidades.Foto;
//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
//import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.PlantaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/planta")
public class PlantaControlador {
    
    @Autowired
    private PlantaServicio plantaServicio;
    
    @GetMapping("/planta")
	public String registro(){
		return"planta-creacion.html";
	}
    @PostMapping("/guardar")
	public String guardar(			
			@RequestParam String nombre,
			@RequestParam Double precio,
			@RequestParam Integer stock,
			@RequestParam String tamanio,
			@RequestParam String descripcion,
			@RequestParam String luz,
			@RequestParam String ubicacion,
			@RequestParam String estilo,
			@RequestParam MultipartFile portada,
			@RequestParam MultipartFile[] imagenes,
                        @RequestParam Boolean destacado,
			Model model
			){					
		try {			
			plantaServicio.cargarPlanta(luz, ubicacion, estilo, nombre, precio, stock, tamanio, descripcion,portada,imagenes, destacado);
			return "index";
		}catch (Exception e) {
model.addAttribute("error", e.getMessage());						
			return "planta-creacion";
		}			
	}
}
