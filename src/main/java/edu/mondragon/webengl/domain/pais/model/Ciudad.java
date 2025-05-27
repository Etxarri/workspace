package edu.mondragon.webengl.domain.pais.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import edu.mondragon.webengl.domain.user.model.Usuario;

@Entity
@Table(name = "ciudad")
public class Ciudad {
    @Id
    @Column(name = "ciudadID")
    private int ciudadID;

    @ManyToOne
    @JoinColumn(name = "comunidadID", nullable = false)
    private ComunidadAutonoma comunidadAutonoma;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;

    @OneToMany(mappedBy = "ciudad")
    private List<Usuario> usuarios;
    // getters y setters

    public int getCiudadID() {
        return ciudadID;
    }

    public void setCiudadID(int ciudadID) {
        this.ciudadID = ciudadID;
    }

    public int getComunidadAutonoma() {
        return comunidadAutonoma.getComunidadID();
    }

    public void setComunidadAutonoma(ComunidadAutonoma comunidadAutonoma) {
        this.comunidadAutonoma = comunidadAutonoma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
