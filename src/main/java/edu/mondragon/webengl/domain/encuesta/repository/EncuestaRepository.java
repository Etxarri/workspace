package edu.mondragon.webengl.domain.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.encuesta.model.Encuesta;
@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Short> {
}

