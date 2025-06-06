package edu.mondragon.webengl.domain.preguntafrecuente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.preguntafrecuente.model.PreguntaFrecuente;

@Repository
public interface PreguntaFrecuenteRepository extends JpaRepository<PreguntaFrecuente, Integer> {
}

