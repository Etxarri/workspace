package edu.mondragon.webengl.domain.user.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recienllegado")
public class RecienLLegado {
    @Id
    @Column(name = "usuarioID")
    private int usuarioID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuarioID")
    private Usuario usuario;

    @Column(length = 50)
    private String necesidades;

    @Column(length = 10)
    private String lenguaje;

    @Column(name = "fecha_llegada")
    private LocalDate fechaLlegada;

    public int getUsuarioID() {
        return usuarioID;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNecesidades() {
        return necesidades;
    }

    public void setNecesidades(String necesidades) {
        this.necesidades = necesidades;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    // getters y setters
    
}