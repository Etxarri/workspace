package edu.mondragon.webengl.domain.foro.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hiloforo")
public class Hiloforo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hiloID;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "preguntaID")
    private int preguntaID;







    public int getHiloID() {
        return hiloID;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setHiloID(int hiloID) {
        this.hiloID = hiloID;
    }

    public int getPreguntaID() {
        return preguntaID;
    }

    public void setPreguntaID(int preguntaID) {
        this.preguntaID = preguntaID;
    }



    
}

