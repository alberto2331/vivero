package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Producto;
import com.vivero.errores.ErrorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vivero.repositorios.FotoRepositorio;
import java.util.List;
import javax.transaction.Transactional;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    @Transactional
    public Foto guardarFoto(MultipartFile[] archivos, Producto producto) throws ErrorServicio{
        if (archivos != null) {        	
            try {
                for(MultipartFile archivo : archivos) {
                	//ok
                	Foto foto = new Foto();
                    foto.setMime(archivo.getContentType());
                    foto.setNombre(archivo.getName());
                    foto.setContenido(archivo.getBytes());
                    foto.setProducto(producto);                    
                    fotoRepositorio.save(foto);	
                }
            	
            } catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }   
}
