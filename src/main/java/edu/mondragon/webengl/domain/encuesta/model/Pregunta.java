package edu.mondragon.webengl.domain.encuesta.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer preguntaID;

    private String texto;
    private String area;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "encuestaID")
    private Encuesta encuesta;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL)
    private List<OpcionRespuesta> opciones;


    // Getters y setters
    public Integer getPreguntaID() {
        return preguntaID;
    }

    public void setPreguntaID(Integer preguntaID) {
        this.preguntaID = preguntaID;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
    public void setOpciones(List<OpcionRespuesta> opciones) {
        this.opciones = opciones;
    }
    public List<OpcionRespuesta> getOpciones() {
        return opciones;
    }
    @Override
    public String toString() {
        return "Pregunta{" +
                "preguntaID=" + preguntaID +
                ", texto='" + texto + '\'' +
                ", area='" + area + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
} 