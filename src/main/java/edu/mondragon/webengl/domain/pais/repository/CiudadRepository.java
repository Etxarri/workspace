package edu.mondragon.webengl.domain.pais.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.pais.model.Ciudad;


@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
}

