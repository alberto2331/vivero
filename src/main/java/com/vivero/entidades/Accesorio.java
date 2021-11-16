package com.vivero.entidades;

import com.vivero.enumeraciones.Tamanio;
import javax.persistence.Entity;

@Entity
public class Accesorio extends Producto{

    public Accesorio() {
    }

    public Accesorio(String id, String tipo, String nombre, Integer precio, Integer stock, Tamanio tamanio, Foto foto, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamanio, foto, descripcion, activo);
    }

    
}