package edu.mondragon.webengl.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.user.model.RecienLLegado;

@Repository
public interface RecienllegadoRepository extends JpaRepository<RecienLLegado, Integer> {
}

