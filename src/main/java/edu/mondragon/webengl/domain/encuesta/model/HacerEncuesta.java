package edu.mondragon.webengl.domain.encuesta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@IdClass(HacerEncuestaId.class)
public class HacerEncuesta {
    @Id
    private int encuestaID;

    @Id
    private int usuarioID;

    @ManyToOne
    @JoinColumn(name = "encuestaID", insertable = false, updatable = false)
    private Encuesta encuesta;

    @ManyToOne
    @JoinColumn(name = "usuarioID", insertable = false, updatable = false)
    private Usuario usuario;

    private String titulo;
    private String descripcion;

    // Campos para las puntuaciones
    private Integer puntosPsicologico;
    private Integer puntosLinguistico;
    private Integer puntosEconomico;
    private Integer puntosPolitico;
    private Integer puntosSocial;
    private Integer puntosNavegacional;
    private Double puntosTotal;
    
    public Integer getPuntosPsicologico() {
        return puntosPsicologico;
    }
    public void setPuntosPsicologico(Integer puntosPsicologico) {
        this.puntosPsicologico = puntosPsicologico;
    }
    public Integer getPuntosLinguistico() {
        return puntosLinguistico;
    }
    public void setPuntosLinguistico(Integer puntosLinguistico) {
        this.puntosLinguistico = puntosLinguistico;
    }
    public Integer getPuntosEconomico() {
        return puntosEconomico;
    }
    public void setPuntosEconomico(Integer puntosEconomico) {
        this.puntosEconomico = puntosEconomico;
    }
    public Integer getPuntosPolitico() {
        return puntosPolitico;
    }
    public void setPuntosPolitico(Integer puntosPolitico) {
        this.puntosPolitico = puntosPolitico;
    }
    public Integer getPuntosSocial() {
        return puntosSocial;
    }
    public void setPuntosSocial(Integer puntosSocial) {
        this.puntosSocial = puntosSocial;
    }
    public Integer getPuntosNavegacional() {
        return puntosNavegacional;
    }
    public void setPuntosNavegacional(Integer puntosNavegacional) {
        this.puntosNavegacional = puntosNavegacional;
    }
    public Double getPuntosTotal() {
        return puntosTotal;
    }
    public void setPuntosTotal(Double puntosTotal) {
        this.puntosTotal = puntosTotal;
    }
    public int getEncuestaID() {
        return encuestaID;
    }
    public void setEncuestaID(int encuestaID) {
        this.encuestaID = encuestaID;
    }
    public int getUsuarioID() {
        return usuarioID;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }
    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
}
