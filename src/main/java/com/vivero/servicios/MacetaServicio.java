package com.vivero.servicios;

import com.vivero.entidades.Foto;
import com.vivero.entidades.Maceta;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.MacetaRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MacetaServicio {

    @Autowired
    private MacetaRepositorio macetaRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void cargarMaceta(String color, String material, String nombre, Double precio, Integer stock, String tamanio, MultipartFile archivo, String descripcion) throws ErrorServicio {
        validar(color,material, nombre, precio, stock, tamanio, archivo, descripcion);
        
        Maceta maceta = new Maceta();
        Foto foto = fotoServicio.guardarFoto(archivo);
        maceta.setFoto(foto);

        maceta.setActivo(Boolean.TRUE);
        maceta.setColor(color);
        maceta.setMaterial(material);
        maceta.setDescripcion(descripcion);
        maceta.setNombre(nombre);
        maceta.setPrecio(precio);
        maceta.setTamanio(tamanio);
        maceta.setStock(stock);
        maceta.setTipo("maceta");
        macetaRepositorio.save(maceta);
    }

    private void validar(String color, String material,String nombre, Double precio, Integer stock, String tamanio, MultipartFile archivo, String descripcion) throws ErrorServicio {

        if (color == null || color.equals("")) {
            throw new ErrorServicio("El campo color no puede quedar vacío");
        }
        if (material == null || material.isEmpty()) {
            throw new ErrorServicio("El campo material no puede quedar vacío");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede quedar vacío");
        }
        if (precio == null || precio<0) {
            throw new ErrorServicio("El precio debe ser igual o mayor a cero");
        }
        if (stock == null || stock.intValue() <= 0) {
            throw new ErrorServicio("El campo stock no puede quedar vacío");
        }
        if (tamanio == null || tamanio.isEmpty()) {
            throw new ErrorServicio("Debe elegir entre: Chico, Mediano o Grande");
        }
        if (archivo == null || archivo.isEmpty()) {
            throw new ErrorServicio("Falta elegir una imagen");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ErrorServicio("La descripción no puede quedar vacía");
        }

    }
}
