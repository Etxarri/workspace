package edu.mondragon.webengl.domain.chat.model;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@Table(name = "mensajechat")
public class MensajeChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mensajeID;

    @ManyToOne
    @JoinColumn(name = "usuarioID", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 1000)
    private String contenido;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false, length = 10)
    private String idioma;

    public int getMensajeID() {
        return mensajeID;
    }

    public void setMensajeID(int mensajeID) {
        this.mensajeID = mensajeID;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    
}
