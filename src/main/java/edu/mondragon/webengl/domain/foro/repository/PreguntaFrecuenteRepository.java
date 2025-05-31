package edu.mondragon.webengl.domain.foro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.foro.model.PreguntaFrecuente;

@Repository
public interface PreguntaFrecuenteRepository extends JpaRepository<PreguntaFrecuente, Integer> {
    List<PreguntaFrecuente> findByTema_TemaID(int temaID);
}

