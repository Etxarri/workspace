package edu.mondragon.webengl.domain.chat.model;

import java.time.LocalDateTime;

public class MensajeChatDTO {

    private Integer usuarioID;
    private String nombre;
    private String contenido;
    private LocalDateTime fechaHora;
    private String idioma;

    public MensajeChatDTO() {
        
    }
    
    public MensajeChatDTO(Integer usuarioID, String nombre, String contenido, LocalDateTime fechaHora, String idioma) {
    this.usuarioID = usuarioID;
    this.nombre = nombre;
    this.contenido = contenido;
    this.fechaHora = fechaHora;
    this.idioma = idioma;
    }


    public Integer getUsuarioID() {
        return usuarioID;
    }
    public void setUsuarioID(Integer usuarioID) {
        this.usuarioID = usuarioID;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    
}

