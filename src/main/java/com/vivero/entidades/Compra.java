package com.vivero.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Compra {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String id;
	@ManyToOne
	private Cliente cliente;
	private String medioPago;
	@ManyToMany
	private List<Planta> planta = new ArrayList<>();  
	@ManyToMany
	private List<Maceta> maceta = new ArrayList<>();
	@ManyToMany
	@JoinColumn
	private List<Accesorio> accesorio = new ArrayList<>();
	
	private Double total;

	public Compra() {
	}
	
	public Compra(String id, Cliente cliente, String medioPago, List<Planta> planta, List<Maceta> maceta,
			List<Accesorio> accesorio, Date fechaCompra, Double total) {
		this.id = id;
		this.cliente = cliente;
		this.medioPago = medioPago;
		this.planta = planta;
		this.maceta = maceta;
		this.accesorio = accesorio;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public List<Planta> getPlanta() {
		return planta;
	}
	public void setPlanta(List<Planta> planta) {
		this.planta = planta;
	}
	public List<Maceta> getMaceta() {
		return maceta;
	}
	public void setMaceta(List<Maceta> maceta) {
		this.maceta = maceta;
	}
	public List<Accesorio> getAccesorio() {
		return accesorio;
	}
	public void setAccesorio(List<Accesorio> accesorio) {
		this.accesorio = accesorio;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
}
