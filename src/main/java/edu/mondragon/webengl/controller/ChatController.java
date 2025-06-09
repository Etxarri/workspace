package edu.mondragon.webengl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.mondragon.webengl.domain.chat.service.MensajeChatService;
import edu.mondragon.webengl.seguridad.UsuarioDetails;


@Controller
@RequestMapping("/chat")
public class ChatController {

    @GetMapping("/{lang}")
    public String verChatIdioma(@PathVariable String lang, Model model, @AuthenticationPrincipal UsuarioDetails usuario) {
        model.addAttribute("idioma", lang);
        model.addAttribute("usuarioLogueado", usuario.getUsuario());
        return "chat/chat"; // Ruta al HTML
    }
}