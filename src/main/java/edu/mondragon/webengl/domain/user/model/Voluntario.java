package edu.mondragon.webengl.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "voluntario")
public class Voluntario {

    @Id
    @Column(name = "usuarioID")
    private int usuarioID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuarioID")
    private Usuario usuario;

    @Column(length = 50)
    private String habilidades;

    @Column(length = 50)
    private String motivacion;

    // Getters y setters

    public int getUsuarioID() {
        return usuarioID;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public String getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }
}
