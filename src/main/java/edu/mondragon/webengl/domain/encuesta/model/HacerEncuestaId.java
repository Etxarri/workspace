package edu.mondragon.webengl.domain.encuesta.model;


public class HacerEncuestaId implements java.io.Serializable {
    private int encuestaID;
    private int usuarioID;

    // equals() y hashCode() deben ser implementados
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HacerEncuestaId)) return false;

        HacerEncuestaId that = (HacerEncuestaId) o;

        if (encuestaID != that.encuestaID) return false;
        return usuarioID == that.usuarioID;
    }
    @Override
    public int hashCode() {
        int result = encuestaID;
        result = 31 * result + usuarioID;
        return result;
    }
    
}
