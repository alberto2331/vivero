package com.vivero.controladores;

import com.vivero.entidades.Planta;
//import com.vivero.entidades.Foto;
//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
//import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.servicios.PlantaServicio;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
        
    //Consulta de plantas-------------------------------------------------------
    @GetMapping("/consultaPlanta")
	public String consultaPlanta(
			ModelMap modelo
			){
    	//Este metodo es para traer todas las plantas de la base de datos
    	List<Planta> plantasFiltradas=plantaServicio.listaPlantas();
    	modelo.put("plantasFiltradas", plantasFiltradas);
		return"consulta-planta";
	}
    
    @GetMapping("/filtrarPlanta")
	public String filtrarPlanta(
			@RequestParam(required=false) String nombre,//ok
			@RequestParam(required=false) Double precioMinimo,//ok
			@RequestParam(required=false) Double precioMaximo,//ok
			@RequestParam(required=false) Integer stock, //consultar como lo vamos a implementar
			@RequestParam(required=false) String tamanio,//ok
			@RequestParam(required=false) String descripcion,//No se me ocurre como implementarlo
			@RequestParam(required=false) String luz,
			@RequestParam(required=false) String ubicacion,//ok
			@RequestParam(required=false) String estilo,//si tiene o no Flor
			//@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
			//@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required=false) Integer destacadoController,//ok
            @RequestParam(required=false) String codigo,//ok
			ModelMap modelo			
			){
    	//Este metodo es para traer todas las plantas de la base de datos QUE COINCIDEN CON LOS FILTROS
    	System.out.println("El destacado que llega al controlador es: "+ destacadoController);
    	if(destacadoController==0) {
    		List<Planta> plantasFiltradas1=plantaServicio.plantasFiltradasSinDestacado
    				(nombre,
        			precioMinimo, 
        			precioMaximo,
        			tamanio,        			
        			codigo,
        			estilo,
        			ubicacion,
        			luz);
    		modelo.put("plantasFiltradas", plantasFiltradas1);
    	}else if(destacadoController==1 || destacadoController==2) {
    		Boolean destacado;
    		if(destacadoController==1) {
    			destacado=true;
    		}else {
    			destacado=false;
    		}
    		List<Planta> plantasFiltradas=plantaServicio.listaPlantasFiltradas(nombre,
        			precioMinimo, 
        			precioMaximo,
        			tamanio,
        			destacado,
        			codigo,
        			estilo,
        			ubicacion,
        			luz);
    		modelo.put("plantasFiltradas", plantasFiltradas);
    	}    	
		return"consulta-planta";
	}

    //Modificar de planta-------------------------------------------------------
    /*El Objeto Planta se define con scope general para poder usarse en los metodos:
    		1) botonModificar
    		2) modificar
    */
    Planta plantaModificar;
    @GetMapping("/modificarPlanta")
	public String modificarPlanta(
			ModelMap modelo
			){
    	//Este metodo es para traer todas las plantas de la base de datos
    	List<Planta> plantasFiltradas=plantaServicio.listaPlantas();
    	modelo.put("plantasFiltradas", plantasFiltradas);
		return"modificar-planta";
    }
    
    @Transactional
	@GetMapping("/botonModificar")
    //Este metodo es obtener la planta a modificar por medio del id que provee el admin
	public String botonModificar(ModelMap modelo,
						   @RequestParam String id){			
		System.out.println("El id es: "+id +"------------------------");			
		try {
			plantaModificar= plantaServicio.buscarPlanta(id);
			modelo.addAttribute("plantaModificar", plantaModificar);
			return "modificar-planta1";
		}catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());								
			return "modificar-planta1";
		}
	}
    
    @Transactional
	@GetMapping("/modificar")
	public String modificar(ModelMap modelo){				
    		System.out.println("La planta de miercoles tiene por nombre: "+plantaModificar.getNombre());			
	    	//modelo.addAttribute("plantaModificar", plantaModificar);
	    	//put("plantaModificar", plantaModificar);																						
			return "modificar-planta1";			    	    	    
	}	
}
