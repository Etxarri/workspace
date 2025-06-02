package edu.mondragon.webengl.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.user.model.Recienllegado;

@Repository
public interface RecienllegadoRepository extends JpaRepository<Recienllegado, Integer>
{
}