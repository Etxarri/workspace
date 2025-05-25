package edu.mondragon.webengl.domain.recurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.recurso.model.RecursoLocal;

@Repository
public interface RecursoLocalRepository extends JpaRepository<RecursoLocal, Integer> {
}

