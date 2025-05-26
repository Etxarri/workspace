package edu.mondragon.webengl.domain.preguntafrecuente.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import edu.mondragon.webengl.domain.foro.model.Temaforo;

@Entity
@Table(name = "preguntafrecuente")
public class PreguntaFrecuente {

    @Id
    @Column(name = "preguntaID")
    private int preguntaID;

    @ManyToOne
    @JoinColumn(name = "temaID", nullable = false)
    private Temaforo tema;

    @Column(nullable = false, length = 50)
    private String pregunta;

    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    // Getters y setters

    public int getPreguntaID() {
        return preguntaID;
    }

    public void setPreguntaID(int preguntaID) {
        this.preguntaID = preguntaID;
    }

    public Temaforo getTema() {
        return tema;
    }

    public void setTema(Temaforo tema) {
        this.tema = tema;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

