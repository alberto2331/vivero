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
    private String tipo;
    private String nombre;
    private Double precio;
    private Integer stock;
    //@Enumerated(EnumType.STRING)
    private String tamanio;
    @OneToOne
    private Foto foto;
    //private List<MultipartFile> galeria;
    private String descripcion;
    private Boolean activo;

    public Producto() {
    }

    public Producto(String id, String tipo, String nombre, Double precio, Integer stock, String tamanio, Foto foto, List<MultipartFile> galeria, String descripcion, Boolean activo) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tamanio = tamanio;
        this.foto = foto;
        //this.galeria = galeria;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    /*public List<MultipartFile> getGaleria() {
        return galeria;
    }

    public void setGaleria(List<MultipartFile> galeria) {
        this.galeria = galeria;
    }*/

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
