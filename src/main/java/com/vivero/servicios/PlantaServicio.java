package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
import com.vivero.entidades.Portada;
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
    private PortadaServicio portadaServicio;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void cargarPlanta(String luz, String ubicacion, String estilo, String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile portada ,MultipartFile[] imagenes) throws Exception {
    	if(tamanio.length()==0){
			throw new Exception("Debe elegir entre: Chico, Mediano o Grande");
		}
    	if(luz.length()==0){
			throw new Exception("Debe elegir entre: Poca, Media o Mucha");
		}
    	if(estilo.length()==0){
			throw new Exception("Debe elegir entre: Colgante o de tronco");
		}
    	if(ubicacion.length()==0){
			throw new Exception("Debe elegir entre: Interior o Exterior");
		}
    	if(portada==null | portada.isEmpty()) {
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
        
    	  Portada foto = portadaServicio.guardarFoto(portada);
    	  planta.setPortada(foto);
    	  
    	  //Generar codigo de producto:
    	  generadorCodigo(planta);
          planta.setActivo(Boolean.TRUE);
          planta.setDescripcion(descripcion);
          planta.setLuz(luz);
          planta.setNombre(nombre);
          planta.setPrecio(precio);
          planta.setStock(stock);
          planta.setTamanio(tamanio);
          planta.setTipo("planta");
          planta.setEstilo(estilo);
          planta.setUbicacion(ubicacion);
          plantaRepositorio.save(planta);
          fotoServicio.guardarFoto(imagenes, planta);                    
    }
    
    public void generadorCodigo(Planta planta) {
    	Integer numero = (int)(plantaRepositorio.count()+1);
    	Integer numero1= 1000 + numero;
    	String codigo = String.valueOf(numero1);
    	String codigo1 = codigo.substring(1);  
    	planta.setCodigo("P-"+codigo1);    	
    }
}
