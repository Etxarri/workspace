package edu.mondragon.webengl.domain.user.service;


import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @PersistenceContext
    public EntityManager entityManager;

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

    @Override
    @Transactional
    public void crearUsuario(String nombre, String apellido, String username, String email, String password, String tipo, int paisID, int ciudadID) {
        entityManager.createNativeQuery("CALL crear_usuario(?, ?, ?, ?, ?, ?, ?, ?)")
            .setParameter(1, nombre)
            .setParameter(2, apellido)
            .setParameter(3, username)
            .setParameter(4, email)
            .setParameter(5, password)
            .setParameter(6, tipo)
            .setParameter(7, paisID)
            .setParameter(8, ciudadID)
            .executeUpdate();
    }

    @Override
    @Transactional
    public void crearRecienllegado(int usuarioID, String necesidades, String lenguaje, java.time.LocalDate fechaLlegada) {
        entityManager.createNativeQuery("CALL crear_recienllegado(?, ?, ?, ?)")
            .setParameter(1, usuarioID)
            .setParameter(2, necesidades)
            .setParameter(3, lenguaje)
            .setParameter(4, java.sql.Date.valueOf(fechaLlegada))
            .executeUpdate();
    }

    @Override
    @Transactional
    public void crearVoluntario(int usuarioID, String habilidades, String motivacion) {
        entityManager.createNativeQuery("CALL crear_voluntario(?, ?, ?)")
            .setParameter(1, usuarioID)
            .setParameter(2, habilidades)
            .setParameter(3, motivacion)
            .executeUpdate();
    }


}

