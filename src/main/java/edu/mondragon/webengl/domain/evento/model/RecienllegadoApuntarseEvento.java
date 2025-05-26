package edu.mondragon.webengl.domain.evento.model;

import java.time.LocalDate;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import edu.mondragon.webengl.domain.user.model.RecienLLegado;

@Entity
public class RecienllegadoApuntarseEvento {
    @EmbeddedId
    private RecienllegadoEventoId id;

    @ManyToOne
    @JoinColumn(name = "eventoID", insertable = false, updatable = false)
    private EventoLocal evento;

    @ManyToOne
    @JoinColumn(name = "usuarioID", insertable = false, updatable = false)
    private RecienLLegado recienllegado;

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

    public RecienLLegado getRecienllegado() {
        return recienllegado;
    }

    public void setRecienllegado(RecienLLegado recienllegado) {
        this.recienllegado = recienllegado;
    }

    public RecienllegadoEventoId getId() {
        return id;
    }

    public void setId(RecienllegadoEventoId id) {
        this.id = id;
    }
    
}

