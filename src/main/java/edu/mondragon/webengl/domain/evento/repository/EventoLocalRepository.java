package edu.mondragon.webengl.domain.evento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;

@Repository
public interface EventoLocalRepository extends JpaRepository<EventoLocal, Integer> {
    List<EventoLocal> findByCiudad_CiudadID(int ciudadID);
}
