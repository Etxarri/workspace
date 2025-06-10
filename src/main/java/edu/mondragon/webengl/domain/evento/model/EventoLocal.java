package edu.mondragon.webengl.domain.evento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import edu.mondragon.webengl.domain.categoria.model.Categoria;
import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@Table(name = "eventolocal")
public class EventoLocal {
    @Id
    private int eventoID;

    @ManyToOne
    @JoinColumn(name = "usuarioID", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "ciudadID", nullable = false)
    private Ciudad ciudad;

    @ManyToOne
    @JoinColumn(name = "categoriaID", nullable = false)
    private Categoria categoria;

    @ManyToMany (mappedBy = "eventosApuntados")
    private Set<Usuario> usuariosApuntados = new HashSet<>();

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private String lugar;



    public int getEventoID() {
        return eventoID;
    }

    public void setEventoID(int eventoID) {
        this.eventoID = eventoID;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Usuario> getUsuariosApuntados() {
        return usuariosApuntados;
    }
    public void setUsuariosApuntados(Set<Usuario> usuariosApuntados) {
        this.usuariosApuntados = usuariosApuntados;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EventoLocal that = (EventoLocal) o;
    return eventoID == that.eventoID;
}

@Override
public int hashCode() {
    return Integer.hashCode(eventoID);
}
    
}

