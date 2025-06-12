package edu.mondragon.webengl.domain.foro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;

@Repository
public interface ComentarioForoRepository extends JpaRepository<ComentarioForo, Integer> {

    List<ComentarioForo> findByHilo(Hiloforo hilo);

    @Query("SELECT c FROM ComentarioForo c LEFT JOIN c.usuariosQueDieronLike u WHERE c.hilo = :hilo GROUP BY c ORDER BY COUNT(u) DESC")
    List<ComentarioForo> findByHiloOrderByNumLikesDesc(@Param("hilo") Hiloforo hilo);

}

