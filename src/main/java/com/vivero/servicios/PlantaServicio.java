package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
//import com.vivero.entidades.Producto;
//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
//import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.PlantaRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlantaServicio {
    
    @Autowired
    private PlantaRepositorio plantaRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void cargarPlanta(String luz, String ubicacion, String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile archivo, List<MultipartFile> galeria) throws Exception {
        /*Comentarios:
          1) Saqué el "id" de los datos que recibe este metodo ya que el mismo es generado automaticamente por JPA
          2) Saqué el "tipo" de los datos que recibe este metodo ya que si estamos cargando una planta, ese atribut
          	 debe ser si o si "planta"
          3) Saqué "activo", se supone que es un producto activo porqeu se está creando
         */
    	if(tamanio.length()==0){
			throw new Exception("Debe elegir entre: Chico, Mediano o Grande");
		}
    	if(luz.length()==0){
			throw new Exception("Debe elegir entre: Poca, Media o Mucha");
		}
    	if(ubicacion.length()==0){
			throw new Exception("Debe elegir entre: Interior o Exterior");
		}
    	if(archivo==null | archivo.isEmpty()) {
			throw new Exception("El archivo imagen está vacío. Favor de cargar una imagen");
		}
    	if(descripcion==null | descripcion.isEmpty() | descripcion.length()==0) {
			throw new Exception("Falta descripción de la planta creada");
		}
    	if(nombre==null | nombre.isEmpty() | nombre.length()==0) {
			throw new Exception("Falta el nombre de la planta creada");
		}    	
    	if(precio==null | precio<=0){
			throw new Exception("El precio debe ser igual o mayor a cero");
		}
    	if(stock==null | stock<0){
			throw new Exception("El stock ser igual o mayor a cero");
		}    	
    	  Planta planta = new Planta();                          
    	  Foto foto = fotoServicio.guardarFoto(archivo);          
    	  planta.setFoto(foto);
          planta.setGaleria(galeria);
        
          planta.setActivo(Boolean.TRUE);
          planta.setDescripcion(descripcion);
          planta.setLuz(luz);
          planta.setNombre(nombre);
          planta.setPrecio(precio);
          planta.setStock(stock);
          planta.setTamanio(tamanio);
          planta.setTipo("planta");
          planta.setUbicacion(ubicacion);
          plantaRepositorio.save(planta);
    }
    
    /*private void validarDatos(Luz luz, Ubicacion ubicacion, String id, String tipo, String nombre, Integer precio, Integer stock, Tamanio tamanio, Foto foto, String descripcion, Boolean activo) throws ErrorServicio {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErrorServicio("La descripcion no puede ser nula");
        }
    }*/
}
