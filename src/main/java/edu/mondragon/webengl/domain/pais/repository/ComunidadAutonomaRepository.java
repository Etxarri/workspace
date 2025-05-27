package edu.mondragon.webengl.domain.pais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.pais.model.ComunidadAutonoma;

@Repository
public interface ComunidadAutonomaRepository extends JpaRepository<ComunidadAutonoma, Integer> {
}

