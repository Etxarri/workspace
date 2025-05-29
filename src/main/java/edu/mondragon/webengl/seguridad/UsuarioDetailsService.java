package edu.mondragon.webengl.seguridad;

import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioDetailsService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new UsuarioDetails(usuario);
    }
}
