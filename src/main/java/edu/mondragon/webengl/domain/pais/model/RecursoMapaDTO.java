package edu.mondragon.webengl.domain.pais.model;

import java.sql.Time;

public class RecursoMapaDTO {
    private String nombre;
    private double latitud;
    private double longitud;
    private String direccion;
    private String telefono;
    private Time horaAbierto;
    private Time horaCerrado;
    private String descripcion;
    // getters y setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Time getHoraAbierto() {
        return horaAbierto;
    }
    public void setHoraAbierto(Time horaAbierto) {
        this.horaAbierto = horaAbierto;
    }
    public Time getHoraCerrado() {
        return horaCerrado;
    }
    public void setHoraCerrado(Time horaCerrado) {
        this.horaCerrado = horaCerrado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}
