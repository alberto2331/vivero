package com.vivero.entidades;

//import com.vivero.enumeraciones.Tamanio;
import java.util.List;
import javax.persistence.Entity;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Maceta extends Producto{
    
    private String color;
    private String material;

    public Maceta() {
    }

    public Maceta(String color, String material, String id, String tipo, String nombre, Double precio, Integer stock, String tamanio, Foto foto, List<MultipartFile> galeria, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamanio, foto, galeria, descripcion, activo);
        this.color = color;
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }    
}
