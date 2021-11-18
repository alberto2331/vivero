package com.vivero.entidades;

//import com.vivero.enumeraciones.Luz;
//import com.vivero.enumeraciones.Tamanio;
//import com.vivero.enumeraciones.Ubicacion;
import java.util.List;
import javax.persistence.Entity;
import org.springframework.web.multipart.MultipartFile;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;

@Entity
public class Planta extends Producto{
    //@Enumerated(EnumType.STRING)
    private String luz;
    //@Enumerated(EnumType.STRING)
    private String ubicacion;

    public Planta() {
    }

    public Planta(String luz, String ubicacion, String id, String tipo, String nombre, Double precio, Integer stock, String tamanio, Foto foto, List<MultipartFile> galeria, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamanio, foto, galeria, descripcion, activo);
        this.luz = luz;
        this.ubicacion = ubicacion;
    }

    public String getLuz() {
        return luz;
    }

    public void setLuz(String luz) {
        this.luz = luz;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
