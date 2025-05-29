package edu.mondragon.webengl.domain.encuesta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encuesta")
public class Encuesta {
    @Id
    private int encuestaID;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    public int getEncuestaID() {
        return encuestaID;
    }

    public void setEncuestaID(int encuestaID) {
        this.encuestaID = encuestaID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "encuestaID=" + encuestaID +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

