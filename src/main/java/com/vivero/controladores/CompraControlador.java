package com.vivero.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CompraControlador{
	@RequestMapping(value = "/compra",method=RequestMethod.POST)

	public @ResponseBody String filter( @RequestBody String [] compra) {

	    System.out.println("compra" +compra.toString());
	    return "index";
	}	
}
