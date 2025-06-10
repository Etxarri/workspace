package edu.mondragon.webengl.domain.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuesta;
import java.util.Optional;


@Repository
public interface HacerEncuestaRepository extends JpaRepository<HacerEncuesta, Integer> {
    Optional<HacerEncuesta> findFirstByUsuarioIDAndEncuestaIDOrderByEncuestaIDDesc(int usuarioID, int encuestaID);
    Optional<HacerEncuesta> findFirstByUsuarioIDOrderByEncuestaIDDesc(int usuarioID);
    Optional<HacerEncuesta> findByUsuarioIDAndEncuestaID(int usuarioID, int encuestaID);
}
