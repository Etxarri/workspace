package edu.mondragon.webengl.domain.evento.model;

public class RecienllegadoEventoId implements java.io.Serializable {
    private short eventoID;
    private short usuarioID;

    public RecienllegadoEventoId(short usuarioID2, int id) {
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
    public short getEventoID() {
        return eventoID;
    }
    public void setEventoID(short eventoID) {
        this.eventoID = eventoID;
    }
    public short getUsuarioID() {
        return usuarioID;
    }
    public void setUsuarioID(short usuarioID) {
        this.usuarioID = usuarioID;
    }
}
