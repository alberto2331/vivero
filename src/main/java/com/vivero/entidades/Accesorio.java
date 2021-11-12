package com.vivero.entidades;

import com.vivero.enumeraciones.Tamaño;
import javax.persistence.Entity;

@Entity
public class Accesorio extends Producto{

    public Accesorio() {
    }

    public Accesorio(String id, String tipo, String nombre, Integer precio, Integer stock, Tamaño tamaño, Foto foto, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamaño, foto, descripcion, activo);
    }

    
}
