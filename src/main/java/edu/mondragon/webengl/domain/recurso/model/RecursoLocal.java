package edu.mondragon.webengl.domain.recurso.model;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.mondragon.webengl.domain.categoria.model.Categoria;
import edu.mondragon.webengl.domain.pais.model.Ciudad;

@Entity
@Table(name = "recursolocal")
public class RecursoLocal {
    @Id
    private int recursoID;

    @ManyToOne
    @JoinColumn(name = "categoriaID", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "ciudadID", nullable = false)
    private Ciudad ciudad;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
    private String direccion;
    private String telefono;
    private java.sql.Time hora_abierto;
    private java.sql.Time hora_cerrado;
    private Double latitud;
    private Double longitud;

    public int getRecursoID() {
        return recursoID;
    }
    public void setRecursoID(int recursoID) {
        this.recursoID = recursoID;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public Double getLatitud() {
        return latitud;
    }
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return longitud;
    }
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public Ciudad getCiudad() {
        return ciudad;
    }
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public java.sql.Time getHora_abierto() {
        return hora_abierto;
    }
    public void setHora_abierto(java.sql.Time hora_abierto) {
        this.hora_abierto = hora_abierto;
    }
    public java.sql.Time getHora_cerrado() {
        return hora_cerrado;
    }
    public void setHora_cerrado(java.sql.Time hora_cerrado) {
        this.hora_cerrado = hora_cerrado;
    }
    

    
}

