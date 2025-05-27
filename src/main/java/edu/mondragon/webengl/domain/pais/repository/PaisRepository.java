package edu.mondragon.webengl.domain.pais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.pais.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {
}

