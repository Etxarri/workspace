package edu.mondragon.webengl.domain.encuesta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@IdClass(RespuestaUsuarioId.class)
public class RespuestaUsuario {
    @Id
    private Integer usuarioID;

    @Id
    private Integer preguntaID;

    private Integer opcionID;
    private String respuestaTexto;
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "usuarioID", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "preguntaID", insertable = false, updatable = false)
    private Pregunta pregunta;

    @ManyToOne
    @JoinColumn(name = "opcionID", insertable = false, updatable = false)
    private OpcionRespuesta opcion;

    public Integer getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Integer getPreguntaID() {
        return preguntaID;
    }

    public void setPreguntaID(Integer preguntaID) {
        this.preguntaID = preguntaID;
    }

    public Integer getOpcionID() {
        return opcionID;
    }

    public void setOpcionID(Integer opcionID) {
        this.opcionID = opcionID;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public OpcionRespuesta getOpcion() {
        return opcion;
    }

    public void setOpcion(OpcionRespuesta opcion) {
        this.opcion = opcion;
    }


    
}
