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
