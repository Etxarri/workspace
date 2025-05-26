package edu.mondragon.webengl.domain.categoria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Categoria {
    @Id
    private int categoriaID;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    public int getCategoriaID() {
        return categoriaID;
    }
    public void setCategoriaID(int categoriaID) {
        this.categoriaID = categoriaID;
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
    @Override
    public String toString() {
        return "Categoria{" +
                "categoriaID=" + categoriaID +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
    
}
