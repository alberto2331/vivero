package com.vivero.entidades;

import javax.persistence.Entity;

@Entity
public class Planta extends Producto{
    
    private String luz;
    private String ubicacion;

    public Planta() {
    }

    public Planta(String luz, String ubicacion, String id, String tipo, String nombre, Integer precio, Integer stock, String tamaño, Foto foto, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamaño, foto, descripcion, activo);
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
