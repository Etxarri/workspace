package edu.mondragon.webengl.domain.evento.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@Table(name = "usuario_apuntarse_evento")
public class UsuarioApuntarseEvento {
    @EmbeddedId
    private UsuarioEventoId id;

    @ManyToOne
    @JoinColumn(name = "eventoID", insertable = false, updatable = false)
    private EventoLocal evento;

    @ManyToOne
    @JoinColumn(name = "usuarioID", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getEventoID() {
        return id.getEventoID();
    }

    public void setEventoID(int eventoID) {
        this.id.setEventoID(eventoID);
    }

    public int getUsuarioID() {
        return id.getUsuarioID();
    }



    public EventoLocal getEvento() {
        return evento;
    }

    public void setEvento(EventoLocal evento) {
        this.evento = evento;
    }



    public UsuarioEventoId getId() {
        return id;
    }

    public void setId(UsuarioEventoId id) {
        this.id = id;
    }
    
}

