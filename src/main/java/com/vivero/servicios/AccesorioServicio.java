package com.vivero.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vivero.entidades.Accesorio;
import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
import com.vivero.repositorios.AccesorioRepositorio;

@Service
public class AccesorioServicio {

	@Autowired
    private AccesorioRepositorio accesorioRepositorio;
	
	@Autowired
    private FotoServicio fotoServicio;

	@Transactional
    public void cargarAccesorio(String nombre,  Double precio, Integer stock,  String tamanio, String descripcion , MultipartFile archivo, String categoria) throws Exception {
        /*Comentarios:
          1) Saqué el "id" de los datos que recibe este metodo ya que el mismo es generado automaticamente por JPA
          2) Saqué el "tipo" de los datos que recibe este metodo ya que si estamos cargando una planta, ese atribut
          	 debe ser si o si "planta"
          3) Saqué "activo", se supone que es un producto activo porqeu se está creando
         */

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
    	
    	if(archivo==null | archivo.isEmpty()) {
			throw new Exception("El archivo imagen está vacío. Favor de cargar una imagen");
		}
    	if(descripcion==null | descripcion.isEmpty() | descripcion.length()==0) {
			throw new Exception("Falta descripción del accesorio creado");
		}
    	
    	
    	if(categoria==null | categoria.length()==0 | categoria.equals("0")){
			throw new Exception("Debe seleccionar una categoría válida");
		}    	
    		
    	  Accesorio accesorio = new Accesorio();                          
    	  Foto foto = fotoServicio.guardarFoto(archivo);          
    	  accesorio.setFoto(foto);                  
        
          accesorio.setActivo(Boolean.TRUE);
          accesorio.setDescripcion(descripcion);          
          accesorio.setNombre(nombre);
          accesorio.setPrecio(precio);
          accesorio.setStock(stock);
          accesorio.setTamanio(tamanio);
          accesorio.setCategoria(categoria);
          accesorio.setTipo("accesorio");          
          accesorioRepositorio.save(accesorio);
    }

}
