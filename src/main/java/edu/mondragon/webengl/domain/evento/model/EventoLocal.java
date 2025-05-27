package edu.mondragon.webengl.domain.evento.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.mondragon.webengl.domain.categoria.model.Categoria;
import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.user.model.Voluntario;

@Entity
@Table(name = "eventolocal")
public class EventoLocal {
    @Id
    private int eventoID;

    @ManyToOne
    @JoinColumn(name = "ciudadID", nullable = false)
    private Ciudad ciudad;

    @ManyToOne
    @JoinColumn(name = "categoriaID", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "usuarioID", nullable = false)
    private Voluntario usuario;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Timestamp fechaHora;

    @Column(nullable = false)
    private String lugar;
}

