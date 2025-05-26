package edu.mondragon.webengl.domain.evento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.evento.model.RecienllegadoApuntarseEvento;
import edu.mondragon.webengl.domain.evento.model.RecienllegadoEventoId;

@Repository
public interface RecienllegadoApuntarseEventoRepository extends JpaRepository<RecienllegadoApuntarseEvento, RecienllegadoEventoId> {
}

