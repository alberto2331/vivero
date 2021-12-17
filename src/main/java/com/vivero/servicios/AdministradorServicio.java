package com.vivero.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.vivero.entidades.Admin;
import com.vivero.repositorios.AdministradorRepositorio;

@Service
public class AdministradorServicio implements UserDetailsService{
	
	//Entiendo que este service no lo vamos a usar ya que el admin va a ser uno solo ??Pensalo..
	
	@Autowired
	private AdministradorRepositorio administradorRepositorio;
	
	@Transactional
	public Admin save(
			String dni,
			String name,
			String lastname,
			String username,
			String password, 
			String password2
			) throws Exception {
		Admin administrador = new Admin();
		//Validaciones:
		
		if(!findByDni(dni).isEmpty	()) {
			System.out.println(findByDni(dni)+"------------");
			throw new Exception("El dni ya esta registrado");
		}
		
		if(findByUsername(username)!=null) {
			throw new Exception("El usuario ya esta registrado");
		}
		
		if(dni==null || dni.isEmpty()) {
			throw new Exception("El dni no puede estar vacío");
		}
		if(name==null || name.isEmpty()) {
			throw new Exception("El nombre no puede estar vacío");
		}		
		if(lastname==null || lastname.isEmpty()) {
			throw new Exception("El apellido no puede estar vacío");
		}
		if(username==null || username.isEmpty()) {
			throw new Exception("El usuario no puede estar vacío");
		}
		if(password==null || password2==null ||password.isEmpty() || password2.isEmpty()) {
			throw new Exception("Las contraseña no pueden estar vacía");
		}
		if(!password.equals(password2)) {
			throw new Exception("Las contraseñas deben ser iguales");	
		}

		//Encriptar Contraseña:
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //Inicializamos el encriptador de contraseñas
		
		administrador.setDni(dni);
		administrador.setName(name);	
		administrador.setLastName(lastname);
		administrador.setUsername(username);
		administrador.setPassword(encoder.encode(password2)); //Seteamos la contraseña encriptada al usuario
		return administradorRepositorio.save(administrador);
	}
	
	public Optional<Admin> findByDni(String dni){		
		return Optional.ofNullable(administradorRepositorio.findByDni(dni));
	}
	
	public Admin findByUsername(String username) {
		return administradorRepositorio.findByUsername(username);
	}

	@Override// este metodo se va a llamar cuando un usuario se quiera loggear 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Admin administrador = administradorRepositorio.findByUsername(username);
			User user;
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_"+	administrador.getRol()));
			return new User(username, administrador.getPassword(), authorities);
		}catch(Exception e) {
			throw new UsernameNotFoundException("El usuario solicitado no existe");
		}
		
	}
}
