package edu.mondragon.webengl.repository;


import edu.mondragon.webengl.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
