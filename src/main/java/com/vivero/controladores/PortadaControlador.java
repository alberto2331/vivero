package com.vivero.controladores;

//import com.sun.istack.internal.logging.Logger;
import com.vivero.entidades.Planta;
import com.vivero.errores.ErrorServicio;
import com.vivero.servicios.PlantaServicio;
//import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import sun.util.logging.PlatformLogger;

@Controller
@RequestMapping("/foto")
public class PortadaControlador {

    @Autowired
    private PlantaServicio plantaServicio;

    @GetMapping("/planta/{id}")
    public ResponseEntity<byte[]> fotoPlanta(@PathVariable String id) throws ErrorServicio {
        // try{
        Planta planta = plantaServicio.buscarPlanta(id);
        if (planta.getPortada() == null) {
            throw new ErrorServicio("La plata no tiene una foto asignada");
        }
        byte[] foto = planta.getPortada().getContenido();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
    }//catch(ErrorServicio ex){
    //Logger.getLogger(FotoControlador.class.getName().log(Level.SEVERE,null,ex));
    //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //}

}
//}
