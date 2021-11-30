package com.vivero.controladores;

import com.vivero.entidades.Maceta;
import com.vivero.servicios.MacetaServicio;
import java.util.List;
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
    		@RequestParam MultipartFile[] imagenes,
                @RequestParam Boolean destacado
    		) {
        try {
            macetaServicio.cargarMaceta( nombre, precio, stock, tamanio, descripcion, portada, imagenes, color, material, destacado);
            return "index";
        } catch (Exception e) {
  modelo.addAttribute("error", e.getMessage());						
			return "planta-creacion";
        }
    }
    
        @GetMapping("/consultaMaceta")
	public String consultaMaceta(
			ModelMap modelo
			){
    	//Este metodo es para traer todas las plantas de la base de datos
    	List<Maceta> macetas =macetaServicio.listaMaceta();
    	modelo.put("macetas",macetas );
		return"consulta-maceta";
	}
    
    @GetMapping("/filtrarMaceta")
	public String filtrarMaceta(
			@RequestParam(required=false) String nombre,//ok
			@RequestParam(required=false) Double precioMinimo,//ok
			@RequestParam(required=false) Double precioMaximo,//ok
			@RequestParam(required=false) Integer stock, //consultar como lo vamos a implementar
			@RequestParam(required=false) String tamanio,//ok
			@RequestParam(required=false) String descripcion,//No se me ocurre como implementarlo
			@RequestParam(required=false) String color,//ok
			@RequestParam(required=false) String material,//si tiene o no Flor
			//@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
			//@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required=false) Integer destacadoController,//ok
            @RequestParam(required=false) String codigo,//ok
			ModelMap modelo			
			){
    	//Este metodo es para traer todas las plantas de la base de datos QUE COINCIDEN CON LOS FILTROS
    	System.out.println("El destacado que llega al controlador es: "+ destacadoController);
        System.out.println( ""+nombre+ ""+precioMinimo+""+ precioMaximo+""+ tamanio+""+ codigo+""+color+""+material);
    	if(destacadoController==0) {
    		List<Maceta> macetasFiltradas1=macetaServicio.macetasFiltradasSinDestacado
    				(nombre,
        			precioMinimo, 
        			precioMaximo,
        			tamanio,        			
        			codigo,
        			color,
        			material);
    		modelo.put("macetasFiltradas", macetasFiltradas1);
    	}else if(destacadoController==1 || destacadoController==2) {
    		Boolean destacado;
    		if(destacadoController==1) {
    			destacado=true;
    		}else {
    			destacado=false;
    		}
    		List<Maceta> macetasFiltradas=macetaServicio.listaMacetasFiltradas(nombre,
        			precioMinimo, 
        			precioMaximo,
        			tamanio,
        			destacado,
        			codigo,
        			color,
        			material);
    		modelo.put("macetasFiltradas", macetasFiltradas);
    	}    	
		return"consulta-maceta";
	}
    
    
    
    
    
}
