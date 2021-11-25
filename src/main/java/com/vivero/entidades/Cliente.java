package com.vivero.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente {

	@Id
	private Long documento;
	private String nombre;
	private String apellido;
	private String creditCard;
	private String direccion;
	private String lugarEntrega;
	private Boolean activo;
	private String username;
	private String password;
	
	public Cliente() {	
	}

	public Cliente(Long documento, String nombre, String apellido, String creditCard, String direccion,
			String lugarEntrega, Boolean activo, String username, String password) {
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.creditCard = creditCard;
		this.direccion = direccion;
		this.lugarEntrega = lugarEntrega;
		this.activo = activo;
		this.username = username;
		this.password = password;
	}

	public Long getDocumento() {
		return documento;
	}
	public void setDocumento(Long documento) {
		this.documento = documento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getLugarEntrega() {
		return lugarEntrega;
	}
	public void setLugarEntrega(String lugarEntrega) {
		this.lugarEntrega = lugarEntrega;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
