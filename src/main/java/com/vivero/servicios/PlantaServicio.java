package com.vivero.servicios;

import com.vivero.entidades.Accesorio;
import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
import com.vivero.entidades.Portada;
import com.vivero.errores.ErrorServicio;
//import com.vivero.entidades.Producto;
//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
//import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.PlantaRepositorio;
import com.vivero.repositorios.PortadaRepositorio;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlantaServicio {

    @Autowired
    private PlantaRepositorio plantaRepositorio;
    
    @Autowired
    private PortadaRepositorio portadaRepositorio;

    @Autowired
    private PortadaServicio portadaServicio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void cargarPlanta(String luz, String ubicacion, String estilo, String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile portada, MultipartFile[] imagenes, Boolean destacado) throws Exception {

        validar(luz, ubicacion, estilo, nombre, precio, stock, tamanio, descripcion, portada, destacado);
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
        planta.setDestacado(destacado);
        plantaRepositorio.save(planta);
        fotoServicio.guardarFoto(imagenes, planta);
    }

    private void validar(String luz, String ubicacion, String estilo, String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile portada, Boolean destacado) throws Exception {
        if (tamanio.length() == 0) {
            throw new Exception("Debe elegir entre: Chico, Mediano,  Grande o No Definido");
        }
        if (luz.length() == 0) {
            throw new Exception("Debe elegir entre: sombra, media-sombra o sol");
        }
        if (estilo.length() == 0) {
            throw new Exception("Debe elegir entre: Con Flor o Sin Flor");
        }
        if (ubicacion.length() == 0) {
            throw new Exception("Debe elegir entre: Interior, Exterior o Interior/Exterior");
        }
        if (portada == null | portada.isEmpty()) {
            throw new Exception("El archivo imagen está vacío. Favor de cargar una imagen");
        }
        if (descripcion == null | descripcion.isEmpty() | descripcion.length() == 0) {
            throw new Exception("Falta descripción de la planta creada");
        }
        if (nombre == null | nombre.isEmpty() | nombre.length() == 0) {
            throw new Exception("Falta el nombre de la planta creada");
        }
        if (precio == null | precio <= 0) {
            throw new Exception("El precio debe ser igual o mayor a cero");
        }
        if (stock == null | stock < 0) {
            throw new Exception("El stock ser igual o mayor a cero");
        }
        if (destacado == null) {
            throw new Exception("Debe indicar sí o no");
        }
    }    

    private void validarSinPortada(String luz, String ubicacion, String estilo, String nombre, Double precio, Integer stock, String tamanio, String descripcion, Boolean destacado) throws Exception {
        if (tamanio.length() == 0) {
            throw new Exception("Debe elegir entre: Chico, Mediano o Grande");
        }
        if (luz.length() == 0) {
            throw new Exception("Debe elegir entre: Poca, Media o Mucha");
        }
        if (estilo.length() == 0) {
            throw new Exception("Debe elegir entre: Colgante o de tronco");
        }
        if (ubicacion.length() == 0) {
            throw new Exception("Debe elegir entre: Interior o Exterior");
        }
        if (descripcion == null | descripcion.isEmpty() | descripcion.length() == 0) {
            throw new Exception("Falta descripción de la planta creada");
        }
        if (nombre == null | nombre.isEmpty() | nombre.length() == 0) {
            throw new Exception("Falta el nombre de la planta creada");
        }
        if (precio == null | precio <= 0) {
            throw new Exception("El precio debe ser igual o mayor a cero");
        }
        if (stock == null | stock < 0) {
            throw new Exception("El stock ser igual o mayor a cero");
        }
        if (destacado == null) {
            throw new Exception("Debe indicar sí o no");
        }
    }
    
    @Transactional
    public void editarPlantaModificandoPortada(Boolean activo,String id1, String luz, String ubicacion, String estilo, String nombre, Double precio, Integer stock, String tamanio, String descripcion, MultipartFile portada, Boolean destacado) throws Exception {
        validar(luz, ubicacion, estilo, nombre, precio, stock, tamanio, descripcion, portada, destacado);

        Optional<Planta> respuesta = plantaRepositorio.findById(id1);       
        if (respuesta.isPresent()) {
            Planta planta = respuesta.get();

            //Borramos la portada pre-cargada
            String id_portada_Borrar = planta.getPortada().getId();
            portadaRepositorio.deleteById(id_portada_Borrar);
            //Fin de borrar portada pre-cargada---------------------------------
            
            Portada foto = portadaServicio.guardarFoto(portada);
            planta.setActivo(activo);
            planta.setPortada(foto);
            planta.setNombre(nombre);
            planta.setDescripcion(descripcion);
            planta.setDestacado(destacado);
            planta.setEstilo(estilo);
            planta.setLuz(luz);
            planta.setNombre(nombre);
            planta.setPrecio(precio);
            planta.setStock(stock);
            planta.setTamanio(tamanio);
            planta.setUbicacion(ubicacion);
            plantaRepositorio.save(planta);
        }
    }
    
    @Transactional
    public void editarPlantaSinModificarPortada(Boolean activo,String id1, String luz, String ubicacion, String estilo, String nombre, Double precio, Integer stock, String tamanio, String descripcion, Boolean destacado) throws Exception {
    	validarSinPortada(luz, ubicacion, estilo, nombre, precio, stock, tamanio, descripcion, destacado);

        Optional<Planta> respuesta = plantaRepositorio.findById(id1);
        if (respuesta.isPresent()) {
            Planta planta = respuesta.get();
           
            planta.setActivo(activo);
            planta.setNombre(nombre);
            planta.setDescripcion(descripcion);
            planta.setDestacado(destacado);
            planta.setEstilo(estilo);
            planta.setLuz(luz);
            planta.setNombre(nombre);
            planta.setPrecio(precio);
            planta.setStock(stock);
            planta.setTamanio(tamanio);
            planta.setUbicacion(ubicacion);
            plantaRepositorio.save(planta);
        }
    }

    public void generadorCodigo(Planta planta) {
        Integer numero = (int) (plantaRepositorio.count() + 1);
        Integer numero1 = 1000 + numero;
        String codigo = String.valueOf(numero1);
        String codigo1 = codigo.substring(1);
        planta.setCodigo("P-" + codigo1);
    }

    public Planta buscarPlanta(String id) throws ErrorServicio {
        Optional<Planta> Resp = plantaRepositorio.findById(id);
        if (Resp.isPresent()) {
            Planta planta = Resp.get();
            return planta;
        } else {
            throw new ErrorServicio("Planta no encontrada");
        }
    }

    public List<Planta> listaPlantas() {
    	List<Planta> listaPlantas=plantaRepositorio.findAll();
    	return listaPlantas;
    }
    
    public List<Planta> listaPlaActivas() {
        List<Planta> listaPlaActivos = plantaRepositorio.listarPlaActivas(true);
        return listaPlaActivos;
    }

    public List<Planta> listaPlantasFiltradas(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, Boolean destacado, String codigo,String estilo, String ubicacion, String luz) {
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
    	
    	List<Planta> listaPlantas=plantaRepositorio.listarPlantas("%"+nombre+"%",precioMinimo, precioMaximo, "%"+tamanio+"%",destacado, "%"+codigo+"%", "%"+estilo+"%", "%"+ubicacion+"%","%"+luz+"%");
    	return listaPlantas;
    }
    
    public List<Planta> plantasFiltradasSinDestacado(String nombre, Double precioMinimo, Double precioMaximo, String tamanio , String codigo,String estilo, String ubicacion, String luz) {
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
    	
    	List<Planta> listaPlantas=plantaRepositorio.listarPlantasSinDestacado("%"+nombre+"%",precioMinimo, precioMaximo, "%"+tamanio+"%", "%"+codigo+"%", "%"+estilo+"%", "%"+ubicacion+"%","%"+luz+"%");
    	return listaPlantas;
    }
}
