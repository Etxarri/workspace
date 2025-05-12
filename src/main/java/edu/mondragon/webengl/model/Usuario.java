package edu.mondragon.webengl.model;


public class Usuario implements java.io.Serializable /* 2nd characteristic */ {
    private static final long serialVersionUID = 3834633934831160740L;

    private Long id;

    private String nombre;
    private int edad;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }





    // Getters y setters
    

    
}