package edu.mondragon.webengl.domain.foro.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "temaforo")
public class Temaforo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int temaID;

    private String nombre;

    private String descripcion;

    private String logo;


    public int getTemaID() {
        return temaID;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    
}
