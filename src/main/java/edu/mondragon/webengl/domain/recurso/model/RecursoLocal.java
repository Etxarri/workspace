package edu.mondragon.webengl.domain.recurso.model;

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
    private java.sql.Time horario;
    public int getRecursoID() {
        return recursoID;
    }
    public void setRecursoID(int recursoID) {
        this.recursoID = recursoID;
    }
    public Categoria getCategoria() {
        return categoria;
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
    public java.sql.Time getHorario() {
        return horario;
    }
    public void setHorario(java.sql.Time horario) {
        this.horario = horario;
    }

    
}

