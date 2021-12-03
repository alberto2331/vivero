package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Maceta;
import com.vivero.entidades.Portada;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.MacetaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MacetaServicio {

    @Autowired
    private MacetaRepositorio macetaRepositorio;

    @Autowired
    private PortadaServicio portadaServicio;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void cargarMaceta( String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile portada ,MultipartFile[] imagenes, String color, String material, Boolean destacado) throws ErrorServicio {
        validar(color,material, nombre, precio, stock, tamanio, descripcion, portada, imagenes, destacado);
        
        Maceta maceta = new Maceta();

        Portada foto = portadaServicio.guardarFoto(portada);  	  
        maceta.setPortada(foto);
        
  	generadorCodigo(maceta);
        maceta.setActivo(Boolean.TRUE);
        maceta.setColor(color);
        maceta.setMaterial(material);
        maceta.setDescripcion(descripcion);
        maceta.setNombre(nombre);
        maceta.setPrecio(precio);
        maceta.setTamanio(tamanio);
        maceta.setStock(stock);
        maceta.setTipo("maceta");
        maceta.setDestacado(destacado);
        macetaRepositorio.save(maceta);
        fotoServicio.guardarFoto(imagenes, maceta);
        
    }

    private void validar(String color, String material,String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile portada,MultipartFile[] imagenes, Boolean destacado) throws ErrorServicio {
        
        if (color == null || color.equals("")) {
            throw new ErrorServicio("El campo color no puede quedar vacío");
        }
        if (material == null || material.isEmpty()) {
            throw new ErrorServicio("El campo material no puede quedar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede quedar vacío");
        }
        if (precio == null || precio<0) {
            throw new ErrorServicio("El precio debe ser igual o mayor a cero");
        }
        if (stock == null || stock.intValue() <= 0) {
            throw new ErrorServicio("El campo stock no puede quedar vacío");
        }
        if (tamanio == null || tamanio.isEmpty()) {
            throw new ErrorServicio("Debe elegir entre: Chico, Mediano o Grande");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErrorServicio("La descripción no puede quedar vacía");
        }
        if(portada==null | portada.isEmpty()) {
			throw new ErrorServicio("El archivo imagen está vacío. Favor de cargar una imagen");
		}
        if(destacado==null){
			throw new ErrorServicio("Debe indicar sí o no");  
		}   
    }
    
        public void generadorCodigo(Maceta maceta) {
    	Integer numero = (int)(macetaRepositorio.count()+1);
    	Integer numero1= 1000 + numero;
    	String codigo = String.valueOf(numero1);
    	String codigo1 = codigo.substring(1);  
    	maceta.setCodigo("M-"+codigo1);    	
    }
        
           public List<Maceta> listaMaceta() {
    	List<Maceta> listaMacetas= macetaRepositorio.findAll();
    	return listaMacetas;
    }

    public List<Maceta> listaMacetasFiltradas(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, Boolean destacado, String codigo,String color, String material) {
    	//Tratamiendo de precios sin filtro especificados por usuario:
    	if(precioMinimo==null || precioMinimo==0) {
    		precioMinimo=0.0;
    	}
    	if(precioMaximo==null || precioMaximo==0) {
    		precioMaximo=10000000.0;
    	}
    	//Tratamiendo de tamaños sin filtros especificados por usuario:
    	if(tamanio.equals(null)) {
    		tamanio="";
    	}
    	
    	List<Maceta> listaMacetas=macetaRepositorio.listarMacetas("%"+nombre+"%",precioMinimo, precioMaximo, "%"+tamanio+"%",destacado, "%"+codigo+"%", "%"+color+"%", "%"+material+"%");
    	return listaMacetas;
    }
    
    public List<Maceta> macetasFiltradasSinDestacado(String nombre, Double precioMinimo, Double precioMaximo, String tamanio , String codigo,String color, String material) {
    	//Tratamiendo de precios sin filtro especificados por usuario:
    	if(precioMinimo==null || precioMinimo==0) {
    		precioMinimo=0.0;
    	}
    	if(precioMaximo==null || precioMaximo==0) {
    		precioMaximo=10000000.0;
    	}
    	//Tratamiendo de tamaños sin filtros especificados por usuario:
    	if(tamanio.equals(null)) {
    		tamanio="";
    	}
    	
    	List<Maceta> listaMacetas=macetaRepositorio.listarMacetasSinDestacado("%"+nombre+"%",precioMinimo, precioMaximo, "%"+tamanio+"%", "%"+codigo+"%", "%"+color+"%","%"+material+"%");
    	return listaMacetas;
    }
        
    //los siguientes metodos son para buscar maceta
    
    public Maceta buscarMaceta(String id) throws ErrorServicio {
        Optional<Maceta> Resp = macetaRepositorio.findById(id);
        if (Resp.isPresent()) {
            Maceta maceta = Resp.get();
            return maceta;
        } else {
            throw new ErrorServicio("Maceta no encontrada");
        }
    }
        
        
}
