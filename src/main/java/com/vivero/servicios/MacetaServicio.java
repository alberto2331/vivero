package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Maceta;
import com.vivero.entidades.Portada;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.MacetaRepositorio;
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
}
