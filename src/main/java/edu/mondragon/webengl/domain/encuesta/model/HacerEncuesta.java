package edu.mondragon.webengl.domain.encuesta.model;


//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import edu.mondragon.webengl.domain.user.model.Usuario;


@Entity
@Table(name = "hacer_encuesta")
@IdClass(HacerEncuestaId.class)
public class HacerEncuesta {
    @Id
    private int encuestaID;

    @Id
    private int usuarioID;

    @ManyToOne
    @JoinColumn(name = "encuestaID", insertable = false, updatable = false)
    private Encuesta encuesta;

    @ManyToOne
    @JoinColumn(name = "usuarioID", insertable = false, updatable = false)
    private Usuario usuario;

    //@Column(name = "titulo")
    private String titulo;

    //@Column(name = "descripcion")
    private String descripcion;

    // Campos para las puntuaciones
    //@Column(name = "resultadoPsicologico", nullable = false)
    private double resultadoPsicologico;

    //@Column(name = "resultadoLinguistico", nullable = false)
    private double resultadoLinguistico;

    //@Column(name = "resultadoEconomico", nullable = false)
    private double resultadoEconomico;

    //@Column(name = "resultadoPolitico", nullable = false)
    private double resultadoPolitico;

    //@Column(name = "resultadoSocial", nullable = false)
    private double resultadoSocial;

    //@Column(name = "resultadoNavegacional", nullable = false)
    private double resultadoNavegacional;

    //@Column(name = "resultadoTotal", nullable = false)
    private double resultadoTotal;
    
    public double getResultadoPsicologico() {
        return resultadoPsicologico;
    }
    public void setResultadoPsicologico(double resultadoPsicologico) {
        this.resultadoPsicologico = resultadoPsicologico;
    }
    public double getResultadoLinguistico() {
        return resultadoLinguistico;
    }
    public void setResultadoLinguistico(double resultadoLinguistico) {
        this.resultadoLinguistico = resultadoLinguistico;
    }
    public double getResultadoEconomico() {
        return resultadoEconomico;
    }
    public void setResultadoEconomico(double resultadoEconomico) {
        this.resultadoEconomico = resultadoEconomico;
    }
    public double getResultadoPolitico() {
        return resultadoPolitico;
    }
    public void setResultadoPolitico(double resultadoPolitico) {
        this.resultadoPolitico = resultadoPolitico;
    }
    public double getResultadoSocial() {
        return resultadoSocial;
    }
    public void setResultadoSocial(double resultadoSocial) {
        this.resultadoSocial = resultadoSocial;
    }
    public double getResultadoNavegacional() {
        return resultadoNavegacional;
    }
    public void setResultadoNavegacional(double resultadoNavegacional) {
        this.resultadoNavegacional = resultadoNavegacional;
    }
    public double getResultadoTotal() {
        return resultadoTotal;
    }
    public void setResultadoTotal(double resultadoTotal) {
        this.resultadoTotal = resultadoTotal;
    }
    public int getEncuestaID() {
        return encuestaID;
    }
    public void setEncuestaID(int encuestaID) {
        this.encuestaID = encuestaID;
    }
    public int getUsuarioID() {
        return usuarioID;
    }
    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }
    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}