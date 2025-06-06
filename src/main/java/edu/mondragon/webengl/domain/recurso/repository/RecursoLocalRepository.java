package edu.mondragon.webengl.domain.recurso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mondragon.webengl.domain.recurso.model.RecursoLocal;

@Repository
public interface RecursoLocalRepository extends JpaRepository<RecursoLocal, Integer> {
    List<RecursoLocal> findByCiudad_CiudadID(int ciudadID);

    List<RecursoLocal> findByCiudad_ComunidadAutonoma_ComunidadID(int comunidadId);
    List<RecursoLocal> findByCiudad_ComunidadAutonoma_ComunidadIDAndCategoria_CategoriaID(int comunidadId, int categoriaID);

    List<RecursoLocal> findByCiudad_CiudadIDAndCategoria_CategoriaID(Integer ciudadId, Integer categoriaId);
}

