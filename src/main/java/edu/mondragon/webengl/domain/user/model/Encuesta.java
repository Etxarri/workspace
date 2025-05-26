package edu.mondragon.webengl.domain.user.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import org.springframework.data.annotation.Id;

@Entity
public class Encuesta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Psicologico
    private double psicologicoConexion;
    private double psicologicoExtranjero;
    private double psicologicoFuturo;
    private double psicologicoAislamiento;

    // Linguistico
    private double linguisticoLectura;
    private double linguisticoConversacion;
    private double linguisticoEscritura;
    private double linguisticoEscucha;

    // Economico
    private double economicoIngreso;
    private double economicoSituacion;
    private double economicoGasto;
    private double economicoSatisfaccion;

    // Politico
    private double politicoComprension;
    private double politicoFrecuencia;
    private double politicoActualidad;
    private double politicoAcciones;

    // Social
    private double socialFrecuencia;
    private double socialConversacion;
    private double socialActividad;
    private double socialFavores;

    // Navegacion
    private double navegacionMedico;
    private double navegacionEmpleo;
    private double navegacionJuridico;
    private double navegacionDiaria;
    
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate()
    {
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters y setters
}