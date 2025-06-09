package edu.mondragon.webengl.webSocket;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import edu.mondragon.webengl.domain.foro.model.ComentarioDTO;
import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;
import edu.mondragon.webengl.domain.foro.service.ComentarioForoService;
import edu.mondragon.webengl.domain.user.model.Usuario;

import edu.mondragon.webengl.domain.user.repository.UsuarioRepository; // importa el repositorio

@Controller
public class ComentarioWebSocketController {

    @Autowired
    private ComentarioForoService comentarioService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository; // inyecta el repositorio

    Logger logger = Logger.getLogger(ComentarioWebSocketController.class.getName());

    @MessageMapping("/foro/{hiloID}/comentarios")
    public void recibirComentario(@DestinationVariable Integer hiloID, ComentarioDTO comentarioDTO) {
        ComentarioForo comentario = new ComentarioForo();
        logger.info("\n\n\nPPPPPPPPPPPPP\n\n\n");
        // Busca el usuario por ID
        Usuario usuario = usuarioRepository.findById(comentarioDTO.getUsuarioID()).orElse(null);
        comentario.setUsuario(usuario);

        comentario.setContenido(comentarioDTO.getContenido());

        Hiloforo hilo = new Hiloforo();
        hilo.setHiloID(hiloID);
        comentario.setHilo(hilo);

        ComentarioForo guardado = comentarioService.guardarComentario(comentario);

        ComentarioDTO respuesta = new ComentarioDTO();
        respuesta.setComentarioID(guardado.getComentarioID());
        respuesta.setUsuarioID(guardado.getUsuario().getUsuarioID());
        respuesta.setNombre(guardado.getUsuario().getNombre());
        respuesta.setContenido(guardado.getContenido());
        respuesta.setFechaHora(guardado.getFechaHora());
        respuesta.setVotos(guardado.getVotos());

        messagingTemplate.convertAndSend("/topic/foro/" + hiloID, respuesta);
    }

    @MessageMapping("/comentario/{comentarioID}/voto/{tipo}")
    public void actualizarVoto(@DestinationVariable Integer comentarioID,@DestinationVariable String tipo) {

    boolean esPositivo = tipo.equals("up");
    ComentarioForo actualizado = comentarioService.actualizarVoto(comentarioID, esPositivo);

    ComentarioDTO dto = new ComentarioDTO();
    dto.setComentarioID(actualizado.getComentarioID());
    dto.setVotos(actualizado.getVotos());

    messagingTemplate.convertAndSend("/topic/comentario/votos", dto);
    }

}