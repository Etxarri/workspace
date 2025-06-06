package edu.mondragon.webengl.domain.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuesta;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuestaId;
import java.util.Optional;


@Repository
public interface HacerEncuestaRepository extends JpaRepository<HacerEncuesta, HacerEncuestaId>
{
    Optional<HacerEncuesta> findFirstByUsuarioIDOrderByEncuestaIDDesc(int usuarioID);
}
