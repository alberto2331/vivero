package com.vivero.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vivero.entidades.Accesorio;
import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
import com.vivero.entidades.Portada;
import com.vivero.repositorios.AccesorioRepositorio;

@Service
public class AccesorioServicio {

	@Autowired
    private AccesorioRepositorio accesorioRepositorio;
	
	@Autowired
    private PortadaServicio portadaServicio;
	
	@Autowired
    private FotoServicio fotoServicio;

	@Transactional
    public void cargarAccesorio(String nombre, Double precio, Integer stock, String tamanio, String descripcion, String categoria, MultipartFile portada, MultipartFile[] imagenes, Boolean destacado) throws Exception {

		if(nombre==null | nombre.isEmpty() | nombre.length()==0) {
			throw new Exception("Falta el nombre de la planta creada");
		}
		
		if(precio==null | precio<=0){
			throw new Exception("El precio debe ser igual o mayor a cero");
		}
		
		if(stock==null | stock<0){
			throw new Exception("El stock ser igual o mayor a cero");
		}    	
		
		if(tamanio.length()==0 | tamanio.equals("0")){
			throw new Exception("Debe elegir entre: Chico, Mediano o Grande");
		}    	
    	
    	if(descripcion==null | descripcion.isEmpty() | descripcion.length()==0) {
			throw new Exception("Falta descripción del accesorio creado");
		}
    	
    	if(portada==null | portada.isEmpty()) {
			throw new Exception("El archivo imagen está vacío. Favor de cargar una imagen");
		}
    	
    	if(categoria==null | categoria.length()==0 | categoria.equals("0")){
			throw new Exception("Debe seleccionar una categoría válida");
		}    	
        if(destacado==null){
			throw new Exception("Debe indicar sí o no");  
		}    
        	
    	  Accesorio accesorio = new Accesorio();                                                

    	  Portada foto = portadaServicio.guardarFoto(portada);
          accesorio.setPortada(foto);
          
          generadorCodigo(accesorio);
    	  accesorio.setPortada(foto);
          accesorio.setActivo(Boolean.TRUE);
          accesorio.setDescripcion(descripcion);          
          accesorio.setNombre(nombre);
          accesorio.setPrecio(precio);
          accesorio.setStock(stock);
          accesorio.setTamanio(tamanio);
          accesorio.setCategoria(categoria);
          accesorio.setTipo("accesorio");     
          accesorio.setDestacado(destacado);
          accesorioRepositorio.save(accesorio);          
          fotoServicio.guardarFoto(imagenes, accesorio);
    }
    
    public void generadorCodigo(Accesorio accesorio) {
    	Integer numero = (int)(accesorioRepositorio.count()+1);
    	Integer numero1= 1000 + numero;
    	String codigo = String.valueOf(numero1);
    	String codigo1 = codigo.substring(1);  
    	accesorio.setCodigo("A-"+codigo1);    	
    }
}
