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
    private String luz;
    private String ubicacion;
    private String estilo;

    public Planta() {
    }

    public Planta(String luz, String ubicacion, String estilo, String id, String codigo, String tipo, String nombre, Double precio, Integer stock, String tamanio,Portada portada, String descripcion, Boolean activo) {
        super(id, codigo, tipo, nombre, precio, stock, tamanio, portada , descripcion, activo);
        this.luz = luz;
        this.ubicacion = ubicacion;
        this.estilo = estilo;
    }

    public String getLuz() {
        return luz;
    }

    public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
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
