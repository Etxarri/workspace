package edu.mondragon.webengl.domain.foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.foro.model.Temaforo;

@Repository
public interface TemaforoRepository extends JpaRepository<Temaforo, Integer> {
}

