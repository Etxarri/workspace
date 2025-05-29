package edu.mondragon.webengl.domain.user.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mondragon.webengl.domain.user.model.*;
import edu.mondragon.webengl.domain.user.repository.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    // UsuarioService es la clase que DICE QUÉ va a HACER.
    // UsuarioServiceImpl es la clase que HACE el TRABAJO de la interfaz UsuarioService.

    private final UsuarioRepository usuarioRepository;
    private final RecienllegadoRepository recienllegadoRepository;
    private final VoluntarioRepository voluntarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
        UsuarioRepository usuarioRepository,
        RecienllegadoRepository recienllegadoRepository,
        VoluntarioRepository voluntarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.recienllegadoRepository = recienllegadoRepository;
        this.voluntarioRepository = voluntarioRepository;
    }

    @Override
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    @Override
    public Usuario login(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            System.out.println("Usuario encontrado: " + usuarioOpt.get().getNombre());
            Usuario usuario = usuarioOpt.get();
            //return passwordEncoder.matches(password, usuario.getPassword());
            return usuario;
        }
        System.out.println("Usuario no encontrado: " + username);
        return null;
    }
    /*
     *     @Override
    public Usuario login(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String hash = new String(usuario.getContraseña());
            if (passwordEncoder.matches(password, hash)) {
                return usuario;
            }
        }
        return null;
    }
     */

    @Override
    public Recienllegado findRecienllegadoById(int id) {
        return recienllegadoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void guardarVoluntario(Voluntario voluntario) {
        voluntarioRepository.save(voluntario);
    }

    @Override
    @Transactional
    public void guardarRecienllegado(Recienllegado recien) {
        recienllegadoRepository.save(recien);
    }

    @Override
    public Voluntario findVoluntarioById(int id) {
        return voluntarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario findUsuarioByIdUsuario(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public String encriptarContraseña(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }


}

