package edu.mondragon.webengl.domain.user.service;

import edu.mondragon.webengl.domain.user.model.Usuario;

public interface UsuarioService {

    String encriptarContraseña(String contraseña);

    //PARA MYSQL
    void crearUsuario(String nombre, String apellido, String username, String email, String password, String tipo, int paisID, int ciudadID);
    void crearRecienllegado(int usuarioID, String necesidades, String lenguaje, java.time.LocalDate fechaLlegada);
    void crearVoluntario(int usuarioID, String habilidades, String motivacion);
    void actualizarUsuario(Usuario usuario, String necesidades, String lenguaje, String habilidades, String motivacion);
}

