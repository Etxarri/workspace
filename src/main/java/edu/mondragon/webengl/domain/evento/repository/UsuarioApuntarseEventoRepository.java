package edu.mondragon.webengl.domain.evento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.evento.model.UsuarioEventoId;
import edu.mondragon.webengl.domain.evento.model.UsuarioApuntarseEvento;

@Repository
public interface UsuarioApuntarseEventoRepository extends JpaRepository<UsuarioApuntarseEvento, UsuarioEventoId> {
    List<UsuarioApuntarseEvento> findById_UsuarioID(int usuarioID);

}

