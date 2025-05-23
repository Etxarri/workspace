package edu.mondragon.webengl.domain.evento.model;

import java.time.LocalDate;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import edu.mondragon.webengl.domain.user.model.Recienllegado;

@Entity
public class RecienllegadoApuntarseEvento {
    @EmbeddedId
    private RecienllegadoEventoId id;

    @ManyToOne
    @JoinColumn(name = "eventoID", insertable = false, updatable = false)
    private EventoLocal evento;

    @ManyToOne
    @JoinColumn(name = "usuarioID", insertable = false, updatable = false)
    private Recienllegado recienllegado;

    private LocalDate fechaInscripcion;

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public short getEventoID() {
        return id.getEventoID();
    }

    public void setEventoID(short eventoID) {
        this.id.setEventoID(eventoID);
    }

    public short getUsuarioID() {
        return id.getUsuarioID();
    }

    public void setUsuarioID(short usuarioID) {
        this.id.setUsuarioID(usuarioID);
    }

    public EventoLocal getEvento() {
        return evento;
    }

    public void setEvento(EventoLocal evento) {
        this.evento = evento;
    }

    public Recienllegado getRecienllegado() {
        return recienllegado;
    }

    public void setRecienllegado(Recienllegado recienllegado) {
        this.recienllegado = recienllegado;
    }

    public RecienllegadoEventoId getId() {
        return id;
    }

    public void setId(RecienllegadoEventoId id) {
        this.id = id;
    }
    
}

