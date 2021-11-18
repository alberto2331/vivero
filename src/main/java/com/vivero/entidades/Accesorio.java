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

    public Accesorio(String categoria, String id, String tipo, String nombre, Double precio, Integer stock, String tamanio, Foto foto, List<MultipartFile> galeria, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamanio, foto, galeria, descripcion, activo);
        this.categoria = categoria;
    }
    
    public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
}