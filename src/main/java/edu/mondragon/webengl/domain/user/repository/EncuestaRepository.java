package edu.mondragon.webengl.domain.user.repository;

import edu.mondragon.webengl.domain.user.model.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EncuestaRepository extends JpaRepository<Encuesta, Long>
{

}