package com.vivero.controladores;

import com.vivero.entidades.Accesorio;
import com.vivero.entidades.Foto;
import com.vivero.entidades.Portada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.FotoRepositorio;
import com.vivero.servicios.AccesorioServicio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/accesorio")
public class AccesorioControlador {

    @Autowired
    private AccesorioServicio accesorioServicio;

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @GetMapping("/accesorio")
    public String registro() {
        return "accesorio-creacion.html";
    }

    @PostMapping("/guardar")
    public String guardar(ModelMap modelo,
            @RequestParam String nombre,
            @RequestParam Double precio,
            @RequestParam Integer stock,
            @RequestParam String tamanio,
            @RequestParam String descripcion,
            @RequestParam String categoria,
            @RequestParam MultipartFile portada,
            @RequestParam MultipartFile[] imagenes,
            @RequestParam Boolean destacado
    ) {
        try {
            accesorioServicio.cargarAccesorio(nombre, precio, stock, tamanio, descripcion, categoria, portada, imagenes, destacado);
            return "index";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            modelo.put("error", e.getMessage());
            return "accesorio-creacion";
        }
    }

    //Consulta de accsesorios-------------------------------------------------------
    @GetMapping("/consultaAccesorio")
    public String consultaAccesorio(ModelMap modelo) {
        //Este metodo es para traer todas los Accesorio de la base de datos
        List<Accesorio> accesoriosFiltrados = accesorioServicio.listaAccesorios();
        modelo.put("accesoriosFiltrados", accesoriosFiltrados);
        return "consulta-accesorio";
    }

    @GetMapping("/filtrarAccesorio")
    public String filtrarAccesorio(
            @RequestParam(required = false) String nombre,//ok
            @RequestParam(required = false) Double precioMinimo,//ok
            @RequestParam(required = false) Double precioMaximo,//ok
            @RequestParam(required = false) Integer stock, //consultar como lo vamos a implementar
            @RequestParam(required = false) String tamanio,//ok
            @RequestParam(required = false) String descripcion,//No se me ocurre como implementarlo
            @RequestParam(required = false) String categoria,
            //@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
            //@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required = false) Integer destacadoController,//ok
            @RequestParam(required = false) String codigo,//ok
            ModelMap modelo) {

        //Este metodo es para traer todas los Accesorios de la base de datos QUE COINCIDEN CON LOS FILTROS
        System.out.println("El destacado que llega al controlador es: " + destacadoController);
        if (destacadoController == 0) {
            List<Accesorio> accesoriosFiltrados1 = accesorioServicio.AccesoriosFiltradosSinDestacado(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    codigo,
                    categoria);
            modelo.put("accesoriosFiltrados", accesoriosFiltrados1);
        } else if (destacadoController == 1 || destacadoController == 2) {
            Boolean destacado;
            if (destacadoController == 1) {
                destacado = true;
            } else {
                destacado = false;
            }
            List<Accesorio> accesoriosFiltrados = accesorioServicio.listaAccesoriosFiltrados(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    destacado,
                    codigo,
                    categoria);
            modelo.put("accesoriosFiltrados", accesoriosFiltrados);
        }
        return "consulta-accesorio";
    }

    //Modificacion de accesorio-------------------------------------------------------
    /*El Objeto Accesorio se define con scope general para poder usarse en los metodos:
    		1) botonModificar
    		2) modificar
     */
    Accesorio accesorioModificar;

    @GetMapping("/modAccesorio")
    public String modAccesorio(
            ModelMap modelo
    ) {
        //Este metodo es para traer todas los accesorio de la base de datos
        List<Accesorio> accesoriosFiltrados = accesorioServicio.listaAccesorios();
        modelo.put("accesoriosFiltrados", accesoriosFiltrados);
        return "modificar-accesorio";
    }

    @GetMapping("/modificarAccesorio")
    public String modificarAccesorio(
            @RequestParam(required = false) String nombre,//ok
            @RequestParam(required = false) Double precioMinimo,//ok
            @RequestParam(required = false) Double precioMaximo,//ok
            @RequestParam(required = false) Integer stock, //consultar como lo vamos a implementar
            @RequestParam(required = false) String tamanio,//ok
            @RequestParam(required = false) String descripcion,//No se me ocurre como implementarlo
            @RequestParam(required = false) String categoria,
            //@RequestParam(required=false) MultipartFile portada, -->no aplicar filtro
            //@RequestParam(required=false) MultipartFile[] imagenes, -->no aplicar filtro
            @RequestParam(required = false) Integer destacadoController,//ok
            @RequestParam(required = false) String codigo,//ok
            ModelMap modelo
    ) {

        if (destacadoController == 0) {
            List<Accesorio> accesoriosFiltrados1 = accesorioServicio.AccesoriosFiltradosSinDestacado(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    codigo,
                    categoria);
            modelo.put("accesoriosFiltrados", accesoriosFiltrados1);
        } else if (destacadoController == 1 || destacadoController == 2) {
            Boolean destacado;
            if (destacadoController == 1) {
                destacado = true;
            } else {
                destacado = false;
            }
            List<Accesorio> accesoriosFiltrados = accesorioServicio.listaAccesoriosFiltrados(nombre,
                    precioMinimo,
                    precioMaximo,
                    tamanio,
                    destacado,
                    codigo,
                    categoria);
            modelo.put("accesoriosFiltrados", accesoriosFiltrados);
        }
        return "modificar-accesorio";
    }

    @Transactional
    @GetMapping("/botonModificar")
    //Este metodo es obtener el accesorio a modificar por medio del id que provee el admin
    public String botonModificar(ModelMap modelo,
            @RequestParam String id) {
        try {
            accesorioModificar = accesorioServicio.buscarAccesorio(id);
            //Metodo para obtener la portada:
            modelo.addAttribute("datosPortada", getPortadaByProducto(id));

            //Metodo para obtener las imagenes:			
            modelo.addAttribute("listaFotos", getGaleriaByProducto(id));
            modelo.addAttribute("accesorioModificar", accesorioModificar);
            return "modificar-accesorio1";
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "modificar-accesorio1";
        }
    }

    @Transactional
    @PostMapping("/modificar")
    public String modificar(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double precio,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) String tamanio,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) MultipartFile portada,
            /*@RequestParam (required=false) MultipartFile[] imagenes,
			@RequestParam (required=false) MultipartFile portadaBaseDatos, 
			@RequestParam (required=false) MultipartFile[] fotoBaseDatos,*/
            @RequestParam(required = false) Integer destacado,
            @RequestParam(required = false) String codigo,
            ModelMap modelo) {
        //System.out.println("Imagenes viene con: "+imagenes.length); //Siempre viene con al menos un elemento

        Boolean destacadoBooleano = true;
        if (destacado == 1) {
            destacadoBooleano = true;
        } else {
            destacadoBooleano = false;
        }

        if (!portada.isEmpty()) {
            try {
                accesorioServicio.editarAccesorioModificandoPortada(
                        activo,
                        id,
                        nombre,
                        precio,
                        stock,
                        tamanio,
                        descripcion,
                        categoria,
                        portada,
                        destacadoBooleano);
            } catch (Exception e) {
                throw new Error("Hubo un problema al cargar las modificaciones del producto");
            }
        }

        if (portada.isEmpty()) {
            try {
                accesorioServicio.editarAccesorioSinModificarPortada(activo, id, nombre, precio,
                        stock, tamanio, descripcion, categoria, portada, activo);
            } catch (Exception e) {
                throw new Error("Hubo un problema al cargar las modificaciones del producto");
            }
        }
        return "index";
    }

    //Metodo para mostrar la portada de un producto:
    //El siguiente metodo puede quedarse en este controlador:
    @GetMapping("/portada/{id}")
    public String getPortadaByProducto(@PathVariable String id) throws ErrorServicio {
        Accesorio accesorio = accesorioServicio.buscarAccesorio(id);
        Portada portada = accesorio.getPortada();
        String datosPortada = Base64.encodeBase64String(portada.getContenido());
        return datosPortada;

    }

    //Metodo para mostrar las imagenes de un producto:
    //Listar fotos de galeria: Este metodo lo creo Juan Guardiola en "imagenControlador.java"-------
    //Una vez que juntemos las ramas del front con las del back
    @GetMapping("/galeria/{id}")
    public List<String> getGaleriaByProducto(@PathVariable String id) throws ErrorServicio {
        try {
            System.out.println("Id: " + id);
            List<Foto> fotos = listarFotosDeProducto(id);//El metodo "listarFotosDeProducto" debe esta en FotoServicio 
            System.out.println("Fotos: " + fotos.toString());

            List<String> datosFotos = new ArrayList();

            for (Foto foto : fotos) {
                String base64 = Base64.encodeBase64String(foto.getContenido());
                datosFotos.add(base64);
            }
            return datosFotos;
        } catch (Exception e) {
            throw new ErrorServicio("Hubo un problema al cargar las imagenes del producto");
        }
    }

    //Este metodo lo creo Juan Guardiola en el FotoServicio: 
    //Cambio por JG para listar fotos
    public List<Foto> listarFotosDeProducto(String id) {
        List<Foto> listaFotos = fotoRepositorio.listaFotosDeProducto("%" + id + "%");
        return listaFotos;

    }

    //////////////////////CONTROLADORES PARA MOSTRARA UNA PLANTA//////////////////////////////////////
    Accesorio accesorioMostrar;

    @Transactional
    @GetMapping("/mostrar")
    public String mostrar(ModelMap modelo) {
        return "mostrar-accesorio";
    }

    @Transactional
    @GetMapping("/mostrarAccesorio")
    //Este metodo es para obtener los datos del Accesorio por medio del id
    public String mostrarAccesorio(ModelMap modelo, @RequestParam String id) {
        try {
            accesorioMostrar = accesorioServicio.buscarAccesorio(id);
            //Metodo para obtener la portada:
            modelo.addAttribute("datosPortada", getPortadaByProducto(id));

            //Metodo para obtener las imagenes:			
            modelo.addAttribute("listaFotos", getGaleriaByProducto(id));
            modelo.addAttribute("accesorioMostrar", accesorioMostrar);
            return "mostrar-accesorio";
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
            return "mostrar-accesorio";
        }
    }
}
