package edu.mondragon.webengl.domain.encuesta.model;

public class ConsejoIntegracion
{
    private String nivel;
    private String descripcion;
    private String[] consejos;

    public ConsejoIntegracion(String nivel, String descripcion, String[] consejos)
    {
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.consejos = consejos;
    }

    public String getNivel()
    {
        return nivel;
    }

    public String getDescripcion()
    {
        return descripcion;
    }
    
    public String[] getConsejos()
    {
        return consejos;
    }
}