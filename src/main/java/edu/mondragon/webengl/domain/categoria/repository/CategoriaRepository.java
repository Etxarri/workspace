package edu.mondragon.webengl.domain.categoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.categoria.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar por nombre:
    // List<Categoria> findByNombre(String nombre);
}

