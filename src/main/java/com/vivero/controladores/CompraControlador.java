package com.vivero.controladores;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@Controller
@RequestMapping("/compra")
public class CompraControlador{
	@RequestMapping(value = "/compra",method=RequestMethod.POST)
	@ResponseBody
	public String filter(		
	        @RequestParam(value = "compra", required = false) String compra,
	        HttpServletRequest request, HttpServletResponse response			
			) {

	    System.out.println("compra" +compra.toString());
	    return "index";
	}
	
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
