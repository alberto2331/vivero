package com.vivero.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vivero.entidades.Accesorio;
import com.vivero.entidades.Foto;
import com.vivero.entidades.Planta;
import com.vivero.entidades.Portada;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.AccesorioRepositorio;
import com.vivero.repositorios.PortadaRepositorio;
import java.util.List;
import java.util.Optional;

@Service
public class AccesorioServicio {

    @Autowired
    private AccesorioRepositorio accesorioRepositorio;
    
    @Autowired
    private PortadaRepositorio portadaRepositorio;

    @Autowired
    private PortadaServicio portadaServicio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void cargarAccesorio(String nombre, Double precio, Integer stock, String tamanio, String descripcion, String categoria, MultipartFile portada, MultipartFile[] imagenes, Boolean destacado) throws Exception{
        
        validar(nombre, precio, stock, tamanio, descripcion, categoria, portada, destacado);
        Accesorio accesorio = new Accesorio();

        Portada foto = portadaServicio.guardarFoto(portada);
        accesorio.setPortada(foto);

        //Generar codigo de producto:
        generadorCodigo(accesorio);
        accesorio.setPortada(foto);
        accesorio.setActivo(Boolean.TRUE);
        accesorio.setDescripcion(descripcion);
        accesorio.setNombre(nombre);
        accesorio.setPrecio(precio);
        accesorio.setStock(stock);
        accesorio.setTamanio(tamanio);
        accesorio.setCategoria(categoria);
        accesorio.setTipo("accesorio");
        accesorio.setDestacado(destacado);
        accesorioRepositorio.save(accesorio);
        fotoServicio.guardarFoto(imagenes, accesorio);
    }
    
    private void validar(String nombre, Double precio, Integer stock, String tamanio, String descripcion, String categoria, MultipartFile portada, Boolean destacado) throws Exception{
        
        if (nombre == null | nombre.isEmpty() | nombre.length() == 0) {
            throw new Exception("Falta el nombre de la planta creada");
        }

        if (precio == null | precio <= 0) {
            throw new Exception("El precio debe ser igual o mayor a cero");
        }

        if (stock == null | stock < 0) {
            throw new Exception("El stock ser igual o mayor a cero");
        }

        if (tamanio.length() == 0 | tamanio.equals("0")) {
            throw new Exception("Debe elegir entre: Chico, Mediano o Grande");
        }

        if (descripcion == null | descripcion.isEmpty() | descripcion.length() == 0) {
            throw new Exception("Falta descripción del accesorio creado");
        }

        if (portada == null | portada.isEmpty()) {
            throw new Exception("El archivo imagen está vacío. Favor de cargar una imagen");
        }

        if (categoria == null | categoria.length() == 0 | categoria.equals("0")) {
            throw new Exception("Debe seleccionar una categoría válida");
        }
        if (destacado == null) {
            throw new Exception("Debe indicar sí o no");
        }
    }
    
    private void validarSinPortada(String nombre, Double precio, Integer stock, String tamanio, String descripcion, String categoria, Boolean destacado) throws Exception{
        
        if (nombre == null | nombre.isEmpty() | nombre.length() == 0) {
            throw new Exception("Falta el nombre de la planta creada");
        }

        if (precio == null | precio <= 0) {
            throw new Exception("El precio debe ser igual o mayor a cero");
        }

        if (stock == null | stock < 0) {
            throw new Exception("El stock ser igual o mayor a cero");
        }

        if (tamanio.length() == 0 | tamanio.equals("0")) {
            throw new Exception("Debe elegir entre: Chico, Mediano o Grande");
        }

        if (descripcion == null | descripcion.isEmpty() | descripcion.length() == 0) {
            throw new Exception("Falta descripción del accesorio creado");
        }

        if (categoria == null | categoria.length() == 0 | categoria.equals("0")) {
            throw new Exception("Debe seleccionar una categoría válida");
        }
        if (destacado == null) {
            throw new Exception("Debe indicar sí o no");
        }
    }

    @Transactional
    public void editarAccesorioModificandoPortada(Boolean activo,String id1, String nombre, Double precio, Integer stock, String tamanio, String descripcion, String categoria, MultipartFile portada, Boolean destacado) throws Exception {
        validar(nombre, precio, stock, tamanio, descripcion, categoria, portada, destacado);

        Optional<Accesorio> respuesta = accesorioRepositorio.findById(id1);       
        if (respuesta.isPresent()) {
            Accesorio accesorio = respuesta.get();

            //Borramos la portada pre-cargada
            String id_portada_Borrar = accesorio.getPortada().getId();
            portadaRepositorio.deleteById(id_portada_Borrar);
            //Fin de borrar portada pre-cargada---------------------------------
            
            Portada foto = portadaServicio.guardarFoto(portada);
            accesorio.setActivo(activo);
            accesorio.setNombre(nombre);
            accesorio.setPrecio(precio);
            accesorio.setStock(stock);
            accesorio.setTamanio(tamanio);
            accesorio.setDescripcion(descripcion);
            accesorio.setCategoria(categoria);
            accesorio.setPortada(foto);
            accesorio.setDestacado(destacado);
            accesorioRepositorio.save(accesorio);
        }
    }
    
    @Transactional
    public void editarAccesorioSinModificarPortada(Boolean activo,String id1, String nombre, Double precio, Integer stock, String tamanio, String descripcion, String categoria, MultipartFile portada, Boolean destacado) throws Exception {
        validarSinPortada(nombre, precio, stock, tamanio, descripcion, categoria, destacado);

        Optional<Accesorio> respuesta = accesorioRepositorio.findById(id1);
        if (respuesta.isPresent()) {
            Accesorio accesorio = respuesta.get();
           
            accesorio.setActivo(activo);
            accesorio.setNombre(nombre);
            accesorio.setPrecio(precio);
            accesorio.setStock(stock);
            accesorio.setTamanio(tamanio);
            accesorio.setDescripcion(descripcion);
            accesorio.setCategoria(categoria);
            accesorio.setDestacado(destacado);
            accesorioRepositorio.save(accesorio);
        }
    }
    
    public void generadorCodigo(Accesorio accesorio) {
        Integer numero = (int) (accesorioRepositorio.count() + 1);
        Integer numero1 = 1000 + numero;
        String codigo = String.valueOf(numero1);
        String codigo1 = codigo.substring(1);
        accesorio.setCodigo("A-" + codigo1);
    }

    //el siguiente metodo es para buscar accesorio
    public Accesorio buscarAccesorio(String id) throws ErrorServicio {
        Optional<Accesorio> Resp = accesorioRepositorio.findById(id);
        if (Resp.isPresent()) {
            Accesorio accesorio = Resp.get();
            return accesorio;
        } else {
            throw new ErrorServicio("Maceta no encontrada");
        }
    }
    
    public List<Accesorio> listaAccesorios() {
        List<Accesorio> listaAccesorios = accesorioRepositorio.findAll();
        return listaAccesorios;
    }
    
    public List<Accesorio> listaAcceActivos() {
        List<Accesorio> listaAcceActivos = accesorioRepositorio.listarAccActivos(true);
        return listaAcceActivos;
    }

    public List<Accesorio> listaAccesoriosFiltrados(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, Boolean destacado, String codigo, String categoria) {
        //Tratamiendo de precios sin filtro especificados por usuario:
        if (precioMinimo == null || precioMinimo == 0) {
            precioMinimo = 0.0;
        }
        if (precioMaximo == null || precioMaximo == 0) {
            precioMaximo = 10000000.0;
        }
        //Tratamiendo de tamaños sin filtros especificados por usuario:
        if (tamanio.equals(null)) {
            tamanio = "";
        }

        List<Accesorio> listaAccesorios = accesorioRepositorio.listarAccesorios("%" + nombre + "%", precioMinimo, precioMaximo, "%" + tamanio + "%", destacado, "%" + codigo + "%", "%" + categoria + "%");
        return listaAccesorios;
    }

    public List<Accesorio> AccesoriosFiltradosSinDestacado(String nombre, Double precioMinimo, Double precioMaximo, String tamanio, String codigo, String categoria) {
        //Tratamiendo de precios sin filtro especificados por usuario:
        if (precioMinimo == null || precioMinimo == 0) {
            precioMinimo = 0.0;
        }
        if (precioMaximo == null || precioMaximo == 0) {
            precioMaximo = 10000000.0;
        }
        //Tratamiendo de tamaños sin filtros especificados por usuario:
        if (tamanio.equals(null)) {
            tamanio = "";
        }

        List<Accesorio> listaAccesorios = accesorioRepositorio.listarAccesoriosSinDestacado("%" + nombre + "%", precioMinimo, precioMaximo, "%" + tamanio + "%", "%" + codigo + "%", "%" + categoria + "%");
        return listaAccesorios;
    }
}
