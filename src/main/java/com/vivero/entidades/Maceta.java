package com.vivero.entidades;

import com.vivero.enumeraciones.Tamanio;
import javax.persistence.Entity;

@Entity
public class Maceta extends Producto{
    
    private String color;

    public Maceta() {
    }

    public Maceta(String color, String id, String tipo, String nombre, Integer precio, Integer stock, Tamanio tamanio, Foto foto, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamanio, foto, descripcion, activo);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}
