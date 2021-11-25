package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Portada;
import com.vivero.errores.ErrorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vivero.repositorios.FotoRepositorio;
import com.vivero.repositorios.PortadaRepositorio;

import java.util.List;
import javax.transaction.Transactional;

@Service
public class PortadaServicio {

    @Autowired
    private PortadaRepositorio portadaRepositorio;
    
    @Transactional
    public Portada guardarFoto(MultipartFile archivo) throws ErrorServicio{
        if (archivo != null) {
            try {
                Portada foto = new Portada();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return portadaRepositorio.save(foto);                		
            } catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
}