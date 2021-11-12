package com.vivero.entidades;

import com.vivero.enumeraciones.Luz;
import com.vivero.enumeraciones.Tamaño;
import com.vivero.enumeraciones.Ubicacion;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Planta extends Producto{
    @Enumerated(EnumType.STRING)
    private Luz luz;
    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

    public Planta() {
    }

    public Planta(Luz luz, Ubicacion ubicacion, String id, String tipo, String nombre, Integer precio, Integer stock, Tamaño tamaño, Foto foto, String descripcion, Boolean activo) {
        super(id, tipo, nombre, precio, stock, tamaño, foto, descripcion, activo);
        this.luz = luz;
        this.ubicacion = ubicacion;
    }

    public Luz getLuz() {
        return luz;
    }

    public void setLuz(Luz luz) {
        this.luz = luz;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    
}
