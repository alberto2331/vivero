package com.vivero.controladores;

//import com.vivero.entidades.Foto;
//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
//import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.PlantaServicio;
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
    
    //solo para ver si se pulleo ok:
    @GetMapping("/planta")
	public String registro(){
		return"planta-creacion.html";
		//Este GET tiene por finalidad navegar al form que permite realizar la carga de una planta
		/*TO DO:
		 1) Acordar con la gente del front el nombre de la ruta desde el index al form que permite cargar plantas
		 Por el momento la ruta es: "/planta/planta" 
		 */		
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
			MultipartFile archivo,
			Model model
			){			
		System.out.println(nombre);
		/*Consideraciones:
		 * Si estoy cargando una planta entonces el "tipo" de producto no se lo debo preguntar al usuario
		 * Si estoy creando un producto se supone que es uno activo
		 * Retiré los enum porque en el front trabajaremos con selects:
		 * 		@RequestParam Tamanio tamanio,
		 * 		@RequestParam Luz luz,
		 *	 	@RequestParam Ubicacion ubicacion,
		*/
		try {
			plantaServicio.cargarPlanta(luz, ubicacion, nombre, precio, stock, tamanio, descripcion, archivo);
			return "index";
		}catch (Exception e) {
			model.addAttribute("error", e.getMessage());						
			return "planta-creacion";
		}			
	}
}
