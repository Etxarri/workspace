package edu.mondragon.webengl.domain.pais.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais {
    @Id
    @Column(name = "paisID")
    private int paisID;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "codigo_iso", length = 2, unique = true)
    private String codigoIso;

    @OneToMany(mappedBy = "pais")
    private List<ComunidadAutonoma> comunidades;

    public int getPaisID() {
        return paisID;
    }

    public void setPaisID(int paisID) {
        this.paisID = paisID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIso() {
        return codigoIso;
    }

    public void setCodigoIso(String codigoIso) {
        this.codigoIso = codigoIso;
    }

    public List<ComunidadAutonoma> getComunidades() {
        return comunidades;
    }

    public void setComunidades(List<ComunidadAutonoma> comunidades) {
        this.comunidades = comunidades;
    }

    // getters y setters
    
}