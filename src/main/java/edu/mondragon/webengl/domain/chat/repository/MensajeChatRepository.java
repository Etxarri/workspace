package edu.mondragon.webengl.domain.chat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.mondragon.webengl.domain.chat.model.MensajeChat;

@Repository
public interface MensajeChatRepository extends JpaRepository<MensajeChat, Integer> {
    List<MensajeChat> findByIdiomaOrderByFechaHoraAsc(String idioma);
}
