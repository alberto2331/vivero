package com.vivero.entidades;

//import com.vivero.enumeraciones.Tamanio;
import java.util.List;
import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Producto {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String codigo; //Codigo de identificacion amigable con el usuario
    private String tipo;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String tamanio;
    @OneToOne
    private Portada portada;
    private String descripcion;
    private Boolean activo;    

    public Producto() {
    }

    public Producto(String id, String codigo, String tipo, String nombre, Double precio, Integer stock, String tamanio,Portada portada, String descripcion, Boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tamanio = tamanio;
        this.portada = portada;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Portada getPortada() {
		return portada;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setPortada(Portada portada) {
		this.portada = portada;
	}

	public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
