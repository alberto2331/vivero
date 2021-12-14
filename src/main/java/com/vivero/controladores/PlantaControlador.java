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
@RequestMapping("/planta")
public class PlantaControlador {

    @Autowired
    private PlantaServicio plantaServicio;

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @GetMapping("/planta")
    public String registro() {
        return "planta-creacion.html";
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
    ) {
        try {
            plantaServicio.cargarPlanta(luz, ubicacion, estilo, nombre, precio, stock, tamanio, descripcion, portada, imagenes, destacado);
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "planta-creacion";
        }
    }

    //Consulta de plantas-------------------------------------------------------
    @GetMapping("/consultaPlanta")
    public String consultaPlanta(
            ModelMap modelo
    ) {
        //Este metodo es para traer todas las plantas de la base de datos
        List<Planta> plantasFiltradas = plantaServicio.listaPlantas();
        modelo.put("plantasFiltradas", plantasFiltradas);
        return "consulta-planta";
    }

    @GetMapping("/filtrarPlanta")
    public String filtrarPlanta(
            @RequestParam(required = false) String nombre,//ok
            @RequestParam(required = false) Double precioMinimo,//ok
            @RequestParam(required = false) Double precioMaximo,//ok
            @RequestParam(required = false) Integer stock, //consultar como lo vamos a implementar
            @RequestParam(required = false) String tamanio,//ok
            @RequestParam(required = false) String descripcion,//No se me ocurre como implementarlo
            @RequestParam(required = false) String luz,
            @RequestParam(required = false) String ubicacion,//ok
            @RequestParam(required = false) String estilo,//si tiene o no Flor
            //@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
            //@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required = false) Integer destacadoController,//ok
            @RequestParam(required = false) String codigo,//ok
            ModelMap modelo
    ) {
        //Este metodo es para traer todas las plantas de la base de datos QUE COINCIDEN CON LOS FILTROS
        System.out.println("El destacado que llega al controlador es: " + destacadoController);
        if (destacadoController == 0) {
            List<Planta> plantasFiltradas1 = plantaServicio.plantasFiltradasSinDestacado(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    codigo,
                    estilo,
                    ubicacion,
                    luz);
            modelo.put("plantasFiltradas", plantasFiltradas1);
        } else if (destacadoController == 1 || destacadoController == 2) {
            Boolean destacado;
            if (destacadoController == 1) {
                destacado = true;
            } else {
                destacado = false;
            }
            List<Planta> plantasFiltradas = plantaServicio.listaPlantasFiltradas(nombre,
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
        return "consulta-planta";
    }

    //Modificacion de planta-------------------------------------------------------
    /*El Objeto Planta se define con scope general para poder usarse en los metodos:
    		1) botonModificar
    		2) modificar
     */
    Planta plantaModificar;

    @GetMapping("/modPlanta")
    public String modPlanta(
            ModelMap modelo
    ) {
        //Este metodo es para traer todas las plantas de la base de datos
        List<Planta> plantasFiltradas = plantaServicio.listaPlantas();
        modelo.put("plantasFiltradas", plantasFiltradas);
        return "modificar-planta";
    }

    @GetMapping("/modificarPlanta")
	public String modificarPlanta(
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
    	return"modificar-planta";
    }

    @Transactional
    @GetMapping("/botonModificar")
    //Este metodo es obtener la planta a modificar por medio del id que provee el admin
	public String botonModificar(ModelMap modelo,
						   @RequestParam String id){						
		try {
			plantaModificar= plantaServicio.buscarPlanta(id);
			//Metodo para obtener la portada:
			modelo.addAttribute("datosPortada",getPortadaByProducto(id));
			
			//Metodo para obtener las imagenes:			
			
			modelo.addAttribute("listaFotos",getGaleriaByProducto(id));			
			modelo.addAttribute("plantaModificar", plantaModificar);
			return "modificar-planta1";
		}catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());								
			return "modificar-planta1";
		}
	}
    
    @Transactional
	@PostMapping("/modificar")
	public String modificar(			
			@RequestParam (required=false) String id,
			@RequestParam (required=false) Boolean activo,
			@RequestParam (required=false) String nombre,
			@RequestParam (required=false) Double precio,			
			@RequestParam (required=false) Integer stock, 
			@RequestParam (required=false) String tamanio,
			@RequestParam (required=false) String descripcion,
			@RequestParam (required=false) String luz,
			@RequestParam (required=false) String ubicacion,
			@RequestParam (required=false) String estilo,
			@RequestParam (required=false) MultipartFile portada, 
			/*@RequestParam (required=false) MultipartFile[] imagenes,
			@RequestParam (required=false) MultipartFile portadaBaseDatos, 
			@RequestParam (required=false) MultipartFile[] fotoBaseDatos,*/
            @RequestParam (required=false) Integer destacado,
            @RequestParam (required=false) String codigo,
			ModelMap modelo){				    	
    	//System.out.println("Imagenes viene con: "+imagenes.length); //Siempre viene con al menos un elemento
    	
    	Boolean destacadoBooleano=true;
    	if(destacado==1){
    		destacadoBooleano=true;
    	}else {
    		destacadoBooleano=false;
    	}    	
    	
    	if( !portada.isEmpty()) {
        	try {
    			plantaServicio.editarPlantaModificandoPortada(
    					activo,
    					id,
    					luz, 
    					ubicacion,
    					estilo,
    					nombre,    			
    					precio,
    					stock,
    					tamanio,
    					descripcion, 
    					portada,  
    					destacadoBooleano);
    		} catch (Exception e) {
    			 throw new Error("Hubo un problema al cargar las modificaciones del producto");
    		}		    
    	}    	
    	
    	if( portada.isEmpty()) {
        	try {
    			plantaServicio.editarPlantaSinModificarPortada(
    					activo,
    					id,
    					luz, 
    					ubicacion,
    					estilo,
    					nombre,    			
    					precio,
    					stock,
    					tamanio,
    					descripcion,     					  
    					destacadoBooleano);
    		} catch (Exception e) {
    			 throw new Error("Hubo un problema al cargar las modificaciones del producto");
    		}		    
    	}    	
    	return "index";			    	    	    
	}      
    
    //Metodo para mostrar la portada de un producto:
    //El siguiente metodo puede quedarse en este controlador:
    @GetMapping("/portada/{id}")
    public String getPortadaByProducto(@PathVariable String id) throws ErrorServicio {
        Planta planta = plantaServicio.buscarPlanta(id);
        Portada portada = planta.getPortada();
        String datosPortada = Base64.encodeBase64String(portada.getContenido());
        return datosPortada;

    }

    //Metodo para mostrar las imagenes de un producto:
    //Listar fotos de galeria: Este metodo lo creo Juan Guardiola en "imagenControlador.java"-------
    //Una vez que juntemos las ramas del front con las del back
    @GetMapping("/galeria/{id}")
    public List<String> getGaleriaByProducto(@PathVariable String id) throws ErrorServicio {
        try {
            System.out.println("Id: " + id);
            List<Foto> fotos = listarFotosDeProducto(id);//El metodo "listarFotosDeProducto" debe esta en FotoServicio 
            System.out.println("Fotos: " + fotos.toString());

            List<String> datosFotos = new ArrayList();

            for (Foto foto : fotos) {
                String base64 = Base64.encodeBase64String(foto.getContenido());
                datosFotos.add(base64);
            }

            //model.addAttribute("listaFotos",datosFotos);
            //return "planta-ver";
            return datosFotos;
        } catch (Exception e) {
            throw new ErrorServicio("Hubo un problema al cargar las imagenes del producto");
        }
    }

    //Este metodo lo creo Juan Guardiola en el FotoServicio: 
    //Cambio por JG para listar fotos
    public List<Foto> listarFotosDeProducto(String id) {
        List<Foto> listaFotos = fotoRepositorio.listaFotosDeProducto("%" + id + "%");
        return listaFotos;

    }
    //////////////////////CONTROLADORES PARA MOSTRARA UNA PLANTA//////////////////////////////////////
    Planta plantaMostrar;
     @Transactional
    @GetMapping("/mostrarPlanta")
    //Este metodo devuelve los datos de la maceta al usuario
    public String mostrarPlanta(ModelMap modelo,@RequestParam String id) {
        System.out.println("El id es: " + id + "------------------------");
        try {
            plantaMostrar = plantaServicio.buscarPlanta(id);
            //Metodo para obtener la portada:
            modelo.addAttribute("datosPortada", getPortadaByProducto(id));

            //Metodo para obtener las imagenes:			
            modelo.addAttribute("listaFotos", getGaleriaByProducto(id));
            modelo.addAttribute("plantaMostrar", plantaMostrar);
            return "mostrar-planta";
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "mostrar-planta";
        }
    }

    @Transactional
    @GetMapping("/mostrar")
    public String mostrar(ModelMap modelo) {
        System.out.println("Nombre planta: " + plantaMostrar.getNombre());
        //modelo.addAttribute("plantaModificar", plantaModificar);
        //put("plantaModificar", plantaModificar);																						
        return "mostrar-planta";
    }
    
    /*      
     Borrar. esto deberìamos cambiarlo de controlador. Al de compras si no me equivoco:
     */
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private ProductoRepositorio productoRepositorio;
	
	//La siguiente variable de scope general es para poder pasar la informacion que viene del carrito de un metodo a otro:
	String[] detalleCompraId;
	String[] detalleCompraCantidad;
	
    @RequestMapping(value = "/compra",method=RequestMethod.POST)
	@ResponseBody
	public String filter(		
	        @RequestParam(value = "arrayId[]") String[] arrayId,
	        @RequestParam(value = "arrayCantidad[]") String[] arrayCantidad,	        	       
	        HttpServletRequest request, HttpServletResponse response			       
			) throws ErrorServicio {

    	//Completamos la info que recibimos por parametro del carrito en los Arrays "detalleCompra" de scope general:
        detalleCompraId=arrayId;
        detalleCompraCantidad=arrayCantidad;
        
	    for(int i=0; i<arrayId.length; i++){
	    	Producto producto= productoServicio.buscarProducto(arrayId[i]);
	    	producto.setStock(producto.getStock()-Integer.parseInt(arrayCantidad[i]));
	    	System.out.println(producto.getStock());
	    	productoRepositorio.save(producto);	    
	    }
	    return "index";
	}	

}
