package edu.mondragon.webengl.domain.user.service;


import java.util.Optional;
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
    public Usuario login(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            //return passwordEncoder.matches(password, usuario.getPassword());
            return usuario;
        }
        return null;
    }


    @Override
    public Recienllegado findRecienllegadoById(int id) {
        return recienllegadoRepository.findById((short)id).orElse(null);
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
        return voluntarioRepository.findById((short) id).orElse(null);
    }

    @Override
    public Usuario findUsuarioByIdUsuario(int id) {
        return usuarioRepository.findById((short) id).orElse(null);
    }

    @Override
    public byte[] encriptarContraseña(String contraseña) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'encriptarContraseña'");
    }
}

