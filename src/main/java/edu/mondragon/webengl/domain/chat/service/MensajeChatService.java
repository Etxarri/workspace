package edu.mondragon.webengl.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.mondragon.webengl.domain.chat.model.MensajeChat;
import edu.mondragon.webengl.domain.chat.model.MensajeChatDTO;
import edu.mondragon.webengl.domain.chat.repository.MensajeChatRepository;

@Service
public class MensajeChatService {

    @Autowired
    private MensajeChatRepository chatRepo;

    public List<MensajeChatDTO> obtenerMensajesPorIdioma(String idioma) {
    return chatRepo.findByIdiomaOrderByFechaHoraAsc(idioma).stream()
        .map(m -> new MensajeChatDTO(
            m.getUsuario().getUsuarioID(),
            m.getUsuario().getNombre(),
            m.getContenido(),
            m.getFechaHora(),
            m.getIdioma()
        ))
        .collect(Collectors.toList());
    }

    public MensajeChat guardarMensaje(MensajeChat mensaje) {
        return chatRepo.save(mensaje);
    }

    
}
