package edu.mondragon.webengl.domain.evento.model;

public class RecienllegadoEventoId implements java.io.Serializable {
    private int eventoID;
    private int usuarioID;

    // Constructor vacío requerido por JPA/Hibernate
    public RecienllegadoEventoId() {
    }

    public RecienllegadoEventoId(int usuarioID, int eventoID) {
        this.eventoID = eventoID;
        this.usuarioID = usuarioID;
    }
    // equals() y hashCode() deben ser implementados
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecienllegadoEventoId)) return false;

        RecienllegadoEventoId that = (RecienllegadoEventoId) o;

        if (eventoID != that.eventoID) return false;
        return usuarioID == that.usuarioID;
    }
    @Override
    public int hashCode() {
        int result = eventoID;
        result = 31 * result + usuarioID;
        return result;
    }
    // Getters y setters
    public int getEventoID() {
        return eventoID;
    }
    public void setEventoID(int eventoID) {
        this.eventoID = eventoID;
    }
    public int getUsuarioID() {
        return usuarioID;
    }

}
