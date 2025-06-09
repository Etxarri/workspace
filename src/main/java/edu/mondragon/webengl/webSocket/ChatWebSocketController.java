package edu.mondragon.webengl.webSocket;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import edu.mondragon.webengl.domain.chat.model.MensajeChat;
import edu.mondragon.webengl.domain.chat.model.MensajeChatDTO;
import edu.mondragon.webengl.domain.chat.service.MensajeChatService;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;

@Controller
public class ChatWebSocketController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private MensajeChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{idioma}/mensaje")
    public void enviarMensaje(@DestinationVariable String idioma, MensajeChatDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioID()).orElseThrow();

        MensajeChat mensaje = new MensajeChat();
        mensaje.setUsuario(usuario);
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaHora(LocalDateTime.now());
        mensaje.setIdioma(idioma);

        MensajeChat guardado = chatService.guardarMensaje(mensaje);

        MensajeChatDTO respuesta = new MensajeChatDTO();
        respuesta.setUsuarioID(usuario.getUsuarioID());
        respuesta.setNombre(usuario.getNombre());
        respuesta.setContenido(guardado.getContenido());
        respuesta.setFechaHora(guardado.getFechaHora());
        respuesta.setIdioma(idioma);

        messagingTemplate.convertAndSend("/topic/chat/" + idioma, respuesta);
    }
}

