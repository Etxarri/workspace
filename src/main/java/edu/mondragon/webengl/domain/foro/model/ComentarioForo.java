package edu.mondragon.webengl.domain.foro.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@Table(name = "comentarioforo")
public class ComentarioForo {

    @Id
    @Column(name = "comentarioID")
    private int comentarioID;

    @ManyToOne
    @JoinColumn(name = "hiloID", nullable = false)
    private Hiloforo hilo;

    @ManyToOne
    @JoinColumn(name = "usuarioID") // o el nombre real de la columna FK
    private Usuario usuario;

    @Column(nullable = false, length = 50)
    private String contenido;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;



    @ManyToMany
    @JoinTable(
        name = "comentario_like",
        joinColumns = @JoinColumn(name = "comentarioID"),
        inverseJoinColumns = @JoinColumn(name = "usuarioID")
    )
    private Set<Usuario> usuariosQueDieronLike = new HashSet<>();
    // Getters y setters

    public int getComentarioID() {
        return comentarioID;
    }

    public void setComentarioID(int comentarioID) {
        this.comentarioID = comentarioID;
    }

    public Hiloforo getHilo() {
        return hilo;
    }

    public void setHilo(Hiloforo hilo) {
        this.hilo = hilo;
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

    public Set<Usuario> getUsuariosQueDieronLike() {
        return usuariosQueDieronLike;
    }

    public void setUsuariosQueDieronLike(Set<Usuario> usuariosQueDieronLike) {
        this.usuariosQueDieronLike = usuariosQueDieronLike;
    }

}