package com.vivero.entidades;

//import com.vivero.enumeraciones.Tamanio;
import java.util.List;
import javax.persistence.Entity;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class Accesorio extends Producto{

    private String categoria;

	public Accesorio() {
    }

    public Accesorio(String categoria, String id, String codigo, String tipo, String nombre, Double precio, Integer stock, String tamanio, Portada portada, String descripcion, Boolean activo) {
        super(id, codigo, tipo, nombre, precio, stock, tamanio, portada, descripcion, activo);
        this.categoria = categoria;
    }
    
    public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
}