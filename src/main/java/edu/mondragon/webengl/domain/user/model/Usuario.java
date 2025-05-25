package edu.mondragon.webengl.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.pais.model.Pais;

/*
 * This is a Java Bean.
 * These are the unique characteristics they must have:
 *     -Default, no-argumented constructor.
 *     -It should be serializable and implement Serializable interface.
 *     -It may have a number of properties which can be read or written.
 *     -"getters" and "setters" for those properties.
 */
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioID")
    private int usuarioID;

    @ManyToOne
    @JoinColumn(name = "paisID", nullable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "ciudadID", nullable = false)
    private Ciudad ciudad;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 64)
    private byte[] password;  // En base a VARBINARY(64)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private TipoUsuario tipo;

    @Column(nullable = false, length = 50)
    private String username;

    // getters y setters

    public enum TipoUsuario {
        recienllegado,
        voluntario
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getContraseña() {
        return password;
    }

    public void setContraseña(byte[] contraseña) {
        this.password = contraseña;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}