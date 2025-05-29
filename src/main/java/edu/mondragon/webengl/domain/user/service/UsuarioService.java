package edu.mondragon.webengl.domain.user.service;


import java.util.Optional;
import edu.mondragon.webengl.domain.user.model.Recienllegado;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.model.Voluntario;

public interface UsuarioService {

    // UsuarioService es la clase que DICE QUÉ va a HACER.
    // UsuarioServiceImpl es la clase que HACE el TRABAJO de la interfaz UsuarioService.
    
    boolean existeUsuarioPorEmail(String email);

    Usuario login(String username, String password);

    Recienllegado findRecienllegadoById(int id);
    Voluntario findVoluntarioById(int id);
    Usuario findUsuarioByIdUsuario(int id);

    void guardarUsuario(Usuario usuario);
    void guardarVoluntario(Voluntario voluntario);
    void guardarRecienllegado(Recienllegado recienllegado);

    String encriptarContraseña(String contraseña);

    Optional<Usuario> findUsuarioByEmail(String email);
}

