package edu.mondragon.webengl.seguridad;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder()
            .username(usuario.getUsername())
            .password(new String(usuario.getContraseña()))
            .roles(usuario.getTipo().name())
            .build();
    }
}