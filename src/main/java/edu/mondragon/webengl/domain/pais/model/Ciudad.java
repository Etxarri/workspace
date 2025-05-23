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
    private Short ciudadID;

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
}
