package com.vivero.controladores;

import com.vivero.entidades.Accesorio;
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
import java.util.List;

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
            @RequestParam MultipartFile[] imagenes,
            @RequestParam Boolean destacado
    ) {
        try {
            accesorioServicio.cargarAccesorio(nombre, precio, stock, tamanio, descripcion, categoria, portada, imagenes, destacado);
            return "index";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            modelo.put("error", e.getMessage());
            return "accesorio-creacion";
        }

    }
    
    //Consulta de accsesorios-------------------------------------------------------
    @GetMapping("/consultaAccesorio")
	public String consultaAccesorio(
			ModelMap modelo
			){
    	//Este metodo es para traer todas los Accesorio de la base de datos
    	List<Accesorio> accesoriosFiltrados = accesorioServicio.listaAccesorios();
    	modelo.put("accesoriosFiltrados", accesoriosFiltrados);
		return"consulta-accesorio";
	}
    
    @GetMapping("/filtrarAccesorio")
	public String filtrarAccesorio(
			@RequestParam(required=false) String nombre,//ok
			@RequestParam(required=false) Double precioMinimo,//ok
			@RequestParam(required=false) Double precioMaximo,//ok
			@RequestParam(required=false) Integer stock, //consultar como lo vamos a implementar
			@RequestParam(required=false) String tamanio,//ok
			@RequestParam(required=false) String descripcion,//No se me ocurre como implementarlo
			@RequestParam(required=false) String categoria,
			//@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
			//@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
                        @RequestParam(required=false) Integer destacadoController,//ok
                        @RequestParam(required=false) String codigo,//ok
			ModelMap modelo			
			){
            
    	//Este metodo es para traer todas los Accesorios de la base de datos QUE COINCIDEN CON LOS FILTROS
    	System.out.println("El destacado que llega al controlador es: "+ destacadoController);
    	if(destacadoController==0) {
    		List<Accesorio> accesoriosFiltrados1 = accesorioServicio.AccesoriosFiltradosSinDestacado
    				(nombre,
        			precioMinimo, 
        			precioMaximo,
        			tamanio,        			
        			codigo,
        			categoria);
    		modelo.put("accesoriosFiltrados", accesoriosFiltrados1);
    	}else if(destacadoController==1 || destacadoController==2) {
    		Boolean destacado;
    		if(destacadoController==1) {
    			destacado=true;
    		}else {
    			destacado=false;
    		}
    		List<Accesorio> accesoriosFiltrados = accesorioServicio.listaAccesoriosFiltrados(nombre,
        			precioMinimo, 
        			precioMaximo,
        			tamanio,
        			destacado,
        			codigo,
        			categoria);
    		modelo.put("accesoriosFiltrados", accesoriosFiltrados);
    	}    	
		return"consulta-accesorio";
	}
}
