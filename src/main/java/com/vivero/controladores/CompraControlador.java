package com.vivero.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vivero.entidades.Cliente;
import com.vivero.entidades.Compra;
import com.vivero.repositorios.ClienteRepositorio;
import com.vivero.repositorios.CompraRepositorio;
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

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Autowired
	private CompraRepositorio compraRepositorio;
	
	//La siguiente variable de scope general es para poder pasar la informacion que viene del carrito de un metodo a otro:
	String[] detalleCompraId;
	String[] detalleCompraCantidad;
	
   @RequestMapping(value = "/compra",method=RequestMethod.POST)
	@ResponseBody
	public void filter(
	        @RequestParam(value = "arrayId[]") String[] arrayId,
	        @RequestParam(value = "arrayCantidad[]") String[] arrayCantidad,	        	       
	        HttpServletRequest request, HttpServletResponse response			       
			) throws ErrorServicio {

   	//Completamos la info que recibimos por parametro del carrito en los Arrays "detalleCompra" de scope general:
       detalleCompraId = arrayId;
       detalleCompraCantidad = arrayCantidad;
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
            @RequestParam String dni,
            @RequestParam String direccion,
            @RequestParam String telefono,
            //Falta implementar "detalle de compras" y "total de la compra"
            Model model
    ) throws ErrorServicio {

	   Cliente cliente = new Cliente();
	   cliente.setDni(dni);
	   cliente.setNombre(nombre +" "+ apellido);
	   cliente.setDireccion(direccion);
	   cliente.setTelefono(Long.parseLong(telefono));
	   cliente.setMail(mail);

	   clienteRepositorio.save(cliente);
		Compra compra = new Compra();

		compra.setCliente(cliente);
		List<Producto> productosComprados= new ArrayList<>();
		Double totalCompra =0.0;

		for(int i=0; i<detalleCompraId.length; i++) {
			Producto producto= productoServicio.buscarProducto(detalleCompraId[i]);
			producto.setStock(producto.getStock()-Integer.parseInt(detalleCompraCantidad[i]));
			System.out.println(producto.getStock());
			totalCompra+=producto.getPrecio()*Integer.parseInt(detalleCompraCantidad[i]);
			productoRepositorio.save(producto);
			productosComprados.add(producto);
		}
		compra.setProductos(productosComprados);
		compra.setTotal(totalCompra);
		compra.setMedioPago("Developing");

		compraRepositorio.save(compra);
    	return "index";
    }
}
