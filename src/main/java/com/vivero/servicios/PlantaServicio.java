package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
import com.vivero.entidades.Producto;
import com.vivero.enumeraciones.Luz;
import com.vivero.enumeraciones.Tamanio;
import com.vivero.enumeraciones.Ubicacion;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.PlantaRepositorio;
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
    public void cargarPlanta(Luz luz, Ubicacion ubicacion, String id, String tipo, String nombre, Integer precio, Integer stock, Tamanio tamanio, Foto foto, String descripcion, Boolean activo) throws ErrorServicio {
        
//        validarCodigo(producto.getCodigo());
//        if (archivo != null) {
//            Imagen imagen = imagenServicio.guardar(archivo);
//            producto.setImagen(imagen);
//        }
          
        validarDatos
          Planta planta = new Planta();
          planta.setActivo(Boolean.TRUE);
          planta.setDescripcion(descripcion);
          planta.setFoto(foto);
          planta.setId(id);
          planta.setLuz(luz);
          planta.setNombre(nombre);
          planta.setPrecio(precio);
          planta.setStock(stock);
          planta.setTamanio(tamanio);
          planta.setTipo(tipo);
          planta.setUbicacion(ubicacion);
          plantaRepositorio.save(planta);
    }
    
    private void validarDatos(Luz luz, Ubicacion ubicacion, String id, String tipo, String nombre, Integer precio, Integer stock, Tamanio tamanio, Foto foto, String descripcion, Boolean activo) throws ErrorServicio {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErrorServicio("La descripcion no puede ser nula");
        }
    }
}
