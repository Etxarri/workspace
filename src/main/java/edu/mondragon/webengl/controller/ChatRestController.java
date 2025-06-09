package edu.mondragon.webengl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.mondragon.webengl.domain.chat.model.MensajeChatDTO;
import edu.mondragon.webengl.domain.chat.service.MensajeChatService;

// ChatRestController.java
@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private MensajeChatService chatService;

    @GetMapping("/{idioma}/mensajes")
    public List<MensajeChatDTO> obtenerMensajes(@PathVariable String idioma) {
        return chatService.obtenerMensajesPorIdioma(idioma);
    }
}
