package edu.mondragon.webengl.domain.foro.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;

@Repository
public interface ComentarioForoRepository extends JpaRepository<ComentarioForo, Integer> {

    List<ComentarioForo> findByHilo(Hiloforo hilo);
    List<ComentarioForo> findByHilo_HiloIDOrderByFechaHoraAsc(Integer hiloID);
    List<ComentarioForo> findByHilo_HiloIDOrderByVotosDescFechaHoraAsc(Integer hiloID);
}

