package com.vivero.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vivero.entidades.Producto;
import com.vivero.errores.ErrorServicio;
import com.vivero.repositorios.ProductoRepositorio;
import com.vivero.servicios.ProductoServicio;

@Controller

@RequestMapping("/compra")
public class CompraControlador{
	
    /*      
    Borrar. esto deberìamos cambiarlo de controlador. Al de compras si no me equivoco:
    */
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private ProductoRepositorio productoRepositorio;
	
	//La siguiente variable de scope general es para poder pasar la informacion que viene del carrito de un metodo a otro:
	String[] detalleCompraId;
	String[] detalleCompraCantidad;
	
   @RequestMapping(value = "/compra",method=RequestMethod.POST)
	@ResponseBody
	public String filter(		
	        @RequestParam(value = "arrayId[]") String[] arrayId,
	        @RequestParam(value = "arrayCantidad[]") String[] arrayCantidad,	        	       
	        HttpServletRequest request, HttpServletResponse response			       
			) throws ErrorServicio {

   	//Completamos la info que recibimos por parametro del carrito en los Arrays "detalleCompra" de scope general:
       detalleCompraId=arrayId;
       detalleCompraCantidad=arrayCantidad;
       
	    for(int i=0; i<arrayId.length; i++){
	    	Producto producto= productoServicio.buscarProducto(arrayId[i]);
	    	producto.setStock(producto.getStock()-Integer.parseInt(arrayCantidad[i]));
	    	System.out.println(producto.getStock());
	    	productoRepositorio.save(producto);	    
	    }
	    return "index";
	}
	
	//Fin metodo de carrito------------------------------------------------
	
	//Para navegar al formulario de compra. Entiendo que a este formulario deberíamos ir cuando usuario haga click en "COMPRAR" del carrito:
	//El metodo "guardar" debería esta en el mismo controlador que contiene los:
	//						* String[] detalleCompraId;
	//						* String[] detalleCompraCantidad;
	
    @GetMapping("/formulario")
    public String registro() {
        return "formulario-compra";
    }
    
    @PostMapping("/guardar")
    public String guardar(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam Long dni,
            @RequestParam String direccion,
            @RequestParam String telefono,
            @RequestParam String medioPago,
            //Falta implementar "detalle de compras" y "total de la compra"
            Model model
    ) {
    	return "Felicitaciones";
    }
}
