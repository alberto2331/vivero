package com.vivero.entidades;

//import com.vivero.enumeraciones.Tamanio;
import javax.persistence.Entity;

@Entity
public class Accesorio extends Producto{

    private String categoria;

	public Accesorio() {
    }

    public Accesorio(String id, String tipo, String nombre, Double precio, Integer stock, String tamanio, Foto foto, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamanio, foto, descripcion, activo);
    }
    
    public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
    
}