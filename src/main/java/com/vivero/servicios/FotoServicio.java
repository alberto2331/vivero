package com.vivero.servicios;

import com.vivero.entidades.Foto;
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
    public Foto guardarFoto(MultipartFile archivo) throws ErrorServicio{
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            } catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
//    @Transactional
//    public Foto guardarGaleria(List<MultipartFile> galeria) throws ErrorServicio{
//        if (galeria != null) {
//            try {
//                List<MultipartFile> galeria = new List<MultipartFile>() {};
//                galeria.set(galeria.iterator());
//                galeria.setNombre(galeria.getName());
//                galeria.setContenido(galeria.listIterator());
//                return fotoRepositorio.save(galeria);
//            } catch(Exception e){
//                System.err.println(e.getMessage());
//            }
//        }
//        return null;
//    }
}
