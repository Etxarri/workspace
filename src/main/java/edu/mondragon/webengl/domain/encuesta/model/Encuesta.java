package edu.mondragon.webengl.domain.encuesta.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "encuesta")
public class Encuesta {
    @Id
    private int encuestaID;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    @Column(name = "tipo_encuesta", nullable = false)
    private String tipoEncuesta;

    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;

    // Getters y setters
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

    public String getTipoEncuesta() {
        return tipoEncuesta;
    }

    public void setTipoEncuesta(String tipoEncuesta) {
        this.tipoEncuesta = tipoEncuesta;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "encuestaID=" + encuestaID +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}

