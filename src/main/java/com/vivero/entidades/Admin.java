package com.vivero.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Admin {

    @Id
    private String dni;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String rol;

    public Admin() {
    }

    public Admin(String dni, String name, String lastName, String username, String password, String rol) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
    
}
