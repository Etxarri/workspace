package edu.mondragon.webengl.domain.pais.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "comunidad_autonoma")
public class ComunidadAutonoma {
    @Id
    @Column(name = "comunidadID")
    private int comunidadID;

    @ManyToOne
    @JoinColumn(name = "paisID", nullable = false)
    private Pais pais;

    @Column(nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "comunidadAutonoma")
    private List<Ciudad> ciudades;

    public int getComunidadID() {
        return comunidadID;
    }

    public void setComunidadID(int comunidadID) {
        this.comunidadID = comunidadID;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    // getters y setters

    
}
