package com.vivero.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vivero.servicios.AdministradorServicio;

@Controller
@RequestMapping("/registro")
public class AdministradorControlador {

	@Autowired
	private AdministradorServicio administradorServicio;
	
	@GetMapping("")
	public String registro() {
		return "registro";
	}
	@PostMapping("")
	public String registroSave(
			Model model,
			@RequestParam String dni,//
			@RequestParam String name,//
			@RequestParam String lastname,//
			@RequestParam String username,//
			@RequestParam String password, 
			@RequestParam String password2) {

		try {
			administradorServicio.save(dni, name, lastname, username, password, password2);
			return "redirect:/";	
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "registro";
		}
	}
}
