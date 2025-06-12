package edu.mondragon.webengl.domain.encuesta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OpcionRespuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer opcionID;

    private String texto;
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "preguntaID")
    private Pregunta pregunta;

    public Integer getOpcionID() {
        return opcionID;
    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }


    
}
