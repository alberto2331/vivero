package com.vivero.controladores;

import com.vivero.entidades.Foto;/*Importado momentaneamente para poder mostrar imagenes de productos a modificar
Entiendo que cuando juntemos las ramas de front y back podremos borrar esta importacion y usar el fotoServicio de Juan Guardiola 
 */
import com.vivero.entidades.Maceta;
import com.vivero.entidades.Portada;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.FotoRepositorio;/*Importado momentaneamente para poder mostrar imagenes de productos a modificar
Entiendo que cuando juntemos las ramas de front y back podremos borrar esta importacion y usar el fotoServicio de Juan Guardiola
 */
import com.vivero.servicios.MacetaServicio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/maceta")
public class MacetaControlador {

    @Autowired
    private MacetaServicio macetaServicio;

    @Autowired
    private FotoRepositorio fotoRepositorio;

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
            macetaServicio.cargarMaceta(nombre, precio, stock, tamanio, descripcion, portada, imagenes, color, material, destacado);
            return "index";
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "planta-creacion";
        }
    }

    @GetMapping("/consultaMaceta")
    public String consultaMaceta(
            ModelMap modelo
    ) {
        //Este metodo es para traer todas las plantas de la base de datos
        List<Maceta> macetasFiltradas = macetaServicio.listaMaceta();
        modelo.put("macetasFiltradas", macetasFiltradas);
        return "consulta-maceta";
    }

    @GetMapping("/filtrarMaceta")
    public String filtrarMaceta(
            @RequestParam(required = false) String nombre,//ok
            @RequestParam(required = false) Double precioMinimo,//ok
            @RequestParam(required = false) Double precioMaximo,//ok
            @RequestParam(required = false) Integer stock, //consultar como lo vamos a implementar
            @RequestParam(required = false) String tamanio,//ok
            @RequestParam(required = false) String descripcion,//No se me ocurre como implementarlo
            @RequestParam(required = false) String color,//ok
            @RequestParam(required = false) String material,//si tiene o no Flor
            //@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
            //@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required = false) Integer destacadoController,//ok
            @RequestParam(required = false) String codigo,//ok
            ModelMap modelo
    ) {
        //Este metodo es para traer todas las plantas de la base de datos QUE COINCIDEN CON LOS FILTROS
        System.out.println("El destacado que llega al controlador es: " + destacadoController);
        System.out.println("" + nombre + "" + precioMinimo + "" + precioMaximo + "" + tamanio + "" + codigo + "" + color + "" + material);
        if (destacadoController == 0) {
            List<Maceta> macetasFiltradas1 = macetaServicio.macetasFiltradasSinDestacado(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    codigo,
                    color,
                    material);
            modelo.put("macetasFiltradas", macetasFiltradas1);
        } else if (destacadoController == 1 || destacadoController == 2) {
            Boolean destacado;
            if (destacadoController == 1) {
                destacado = true;
            } else {
                destacado = false;
            }
            List<Maceta> macetasFiltradas = macetaServicio.listaMacetasFiltradas(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    destacado,
                    codigo,
                    color,
                    material);
            modelo.put("macetasFiltradas", macetasFiltradas);
        }
        return "consulta-maceta";
    }

    Maceta macetaMostrar;

    @Transactional
    @GetMapping("/mostrar")
    public String mostrar(ModelMap modelo) {																						
        return "mostrar-maceta";
    }
    
    @Transactional
    @GetMapping("/mostrarMaceta")
    //Este metodo es para obtener los datos de la maceta por medio del id
    public String mostrarMaceta(ModelMap modelo, @RequestParam String id) {
        try {
            macetaMostrar = macetaServicio.buscarMaceta(id);
            //Metodo para obtener la portada:
            modelo.addAttribute("datosPortada", getPortadaByProducto(id));

            //Metodo para obtener las imagenes:			
            modelo.addAttribute("listaFotos", getGaleriaByProducto(id));
            modelo.addAttribute("macetaMostrar", macetaMostrar);
            return "mostrar-maceta";
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "mostrar-maceta";
        }
    }
    //Metodo para mostrar la portada de un producto:
    //El siguiente metodo puede quedarse en este controlador:
    @GetMapping("/portada/{id}")
    public String getPortadaByProducto(@PathVariable String id) throws ErrorServicio {
        Maceta maceta = macetaServicio.buscarMaceta(id);
        Portada portada = maceta.getPortada();
        String datosPortada = Base64.encodeBase64String(portada.getContenido());
        return datosPortada;

    }
    //Metodo para mostrar las imagenes de un producto:
    //Listar fotos de galeria: Este metodo lo creo Juan Guardiola en "imagenControlador.java"-------
    //Una vez que juntemos las ramas del front con las del back
    @GetMapping("/galeria/{id}")
    public List<String> getGaleriaByProducto(@PathVariable String id) throws ErrorServicio {
        try {
            List<Foto> fotos = listarFotosDeProducto(id);//El metodo "listarFotosDeProducto" debe esta en FotoServicio

            List<String> datosFotos = new ArrayList();

            for (Foto foto : fotos) {
                String base64 = Base64.encodeBase64String(foto.getContenido());
                datosFotos.add(base64);
            }
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
    /////////////////////////// MODIFICAR MACETA //////////////////////////////////
    Maceta macetaModificar;
   
    
       @GetMapping("/modMaceta")
    public String modMaceta(
            ModelMap modelo
    ) {
        //Este metodo es para traer todas las plantas de la base de datos
        List<Maceta> macetasFiltradas = macetaServicio.listaMaceta();
        modelo.put("macetasFiltradas", macetasFiltradas);
        return "modificar-maceta";
    }

    @GetMapping("/modificarMaceta")
	public String modificarMaceta(
			@RequestParam(required=false) String nombre,//ok
			@RequestParam(required=false) Double precioMinimo,//ok
			@RequestParam(required=false) Double precioMaximo,//ok
			@RequestParam(required=false) Integer stock, //consultar como lo vamos a implementar
			@RequestParam(required=false) String tamanio,//ok
			@RequestParam(required=false) String descripcion,//No se me ocurre como implementarlo
			@RequestParam(required=false) String color,
			@RequestParam(required=false) String material,//ok
			//@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
			//@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required=false) Integer destacadoController,//ok
            @RequestParam(required=false) String codigo,//ok
			ModelMap modelo			
			){
    	
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
    	return"modificar-maceta";
    }

    @Transactional
    @GetMapping("/botonModificar")
    //Este metodo es obtener la planta a modificar por medio del id que provee el admin
	public String botonModificar(ModelMap modelo,
						   @RequestParam String id){						
		try {
			macetaModificar= macetaServicio.buscarMaceta(id);
			//Metodo para obtener la portada:
			modelo.addAttribute("datosPortada",getPortadaByProducto(id));
			
			//Metodo para obtener las imagenes:			
			
			modelo.addAttribute("listaFotos",getGaleriaByProducto(id));			
			modelo.addAttribute("macetaModificar", macetaModificar);
			return "modificar-maceta1";
		}catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());								
			return "modificar-maceta1";
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
			@RequestParam (required=false) String color,
			@RequestParam (required=false) String material,
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
    			macetaServicio.editarMacetaModificandoPortada(activo, id, nombre, precio, stock, tamanio, descripcion, portada, codigo, color, activo);
    		
    		} catch (Exception e) {
    			 throw new Error("Hubo un problema al cargar las modificaciones del producto");
    		}		    
    	}    	
    	
    	if( portada.isEmpty()) {
        	try {
    			macetaServicio.editarMacetaSinModificarPortada(activo, id, nombre, precio, stock, tamanio, descripcion, codigo, color, activo);
    		} catch (Exception e) {
    			 throw new Error("Hubo un problema al cargar las modificaciones del producto");
    		}		    
    	}    	
    	return "index";			    	    	    
	}      
    
}
