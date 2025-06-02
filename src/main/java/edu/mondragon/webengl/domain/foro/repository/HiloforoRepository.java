package edu.mondragon.webengl.domain.foro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;

@Repository
public interface HiloforoRepository extends JpaRepository<Hiloforo, Integer> {
    Optional<Hiloforo> findByPreguntaID(int preguntaID);
}

