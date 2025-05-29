package edu.mondragon.webengl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import java.util.List;
//import edu.mondragon.webengl.helper.ControllerHelper;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final EventoLocalRepository eventoRepo;

    public LoginController(EventoLocalRepository eventoRepo) {
        this.eventoRepo = eventoRepo;
    }
    
    @GetMapping
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        return "login/login"; // muestra el login.html
    }

    @GetMapping("/logged")
    public String logged(Model model) {
        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        return "login/logged"; // Busca logged.html en templates
    }
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("message", "message.logout");
        return "redirect:/login";
    }
}


