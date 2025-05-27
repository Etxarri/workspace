package edu.mondragon.webengl.domain.recurso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.recurso.model.RecursoLocal;

@Repository
public interface RecursoLocalRepository extends JpaRepository<RecursoLocal, Integer> {
    List<RecursoLocal> findByCiudad_CiudadID(int ciudadID);

    @Query("SELECT r FROM RecursoLocal r WHERE r.ciudad.comunidadAutonoma.comunidadID = :comunidadId AND r.categoria = :categoria")
    List<RecursoLocal> findByComunidadAndCategoria(@Param("comunidadId") int comunidadId, @Param("categoria") String categoria);

    @Query("SELECT DISTINCT r.categoria FROM RecursoLocal r")
    List<String> findAllCategorias();

    @Query("SELECT DISTINCT r.ciudad FROM RecursoLocal r WHERE r.ciudad.comunidadAutonoma.comunidadID = :comunidadId AND r.categoria = :categoria")
    List<Ciudad> findCiudadesByComunidadAndCategoria(@Param("comunidadId") int comunidadId, @Param("categoria") String categoria);
    
}

