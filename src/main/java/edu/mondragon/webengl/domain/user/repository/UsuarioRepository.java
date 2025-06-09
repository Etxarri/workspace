package edu.mondragon.webengl.domain.user.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.user.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    //El optional es una clase en el que el contenido puede contener un usuario(en este caso) o pude estar vacío.
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByusuarioID(int idUsuario);
    boolean existsByUsuarioIDAndEventosApuntados_EventoID(int usuarioId, int eventoId);
    Optional<Usuario> deleteByEventosApuntados_EventoID(int eventoId);

}

