package edu.mondragon.webengl.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import edu.mondragon.webengl.seguridad.UsuarioDetails;
import java.util.List;
//import edu.mondragon.webengl.helper.ControllerHelper;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/login")
public class LoginController
{
    private final EventoLocalRepository eventoRepo;
    private final UsuarioRepository usuarioRepo;

    public LoginController(EventoLocalRepository eventoRepo, UsuarioRepository usuarioRepo)
    {
        this.eventoRepo = eventoRepo;
        this.usuarioRepo = usuarioRepo;
    }
    
    @GetMapping
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null)
        {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }

        return "login/login"; // muestra el login.html
    }

    @PostMapping
    public String login(@ModelAttribute Usuario usuario, 
                       HttpSession session,
                       RedirectAttributes redirectAttrs) {
        
        // Buscar el usuario en la base de datos
        Usuario usuarioEncontrado = usuarioRepo.findByUsername(usuario.getUsername()).orElse(null);

        // Verificar si existe y la contraseña coincide
        if (usuarioEncontrado != null && usuarioEncontrado.getContraseña().equals(usuario.getContraseña()))
        {
            // Login exitoso
            session.setAttribute("usuario", usuarioEncontrado);
            
            // Verificar si hay una URL anterior guardada
            String urlAnterior = (String) session.getAttribute("urlAnterior");
            if (urlAnterior != null && !urlAnterior.contains("/login"))
            {
                session.removeAttribute("urlAnterior"); // limpiar la URL guardada
                return "redirect:" + urlAnterior;
            }
            
            // Si no hay URL anterior, ir a la página principal
            return "redirect:/login/logged";
        }

        // Login fallido
        redirectAttrs.addFlashAttribute("error", "Usuario o contraseña incorrectos");
        return "redirect:/login";
    }

    @GetMapping("/logged")
    public String logged(Model model, HttpSession session, @AuthenticationPrincipal UsuarioDetails user)
    {
        // Verificar que el usuario está en sesión
        Usuario usuario = user.getUsuario();
        if (usuario == null)
        {
            return "redirect:/login";
        }

        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        
        return "login/logged"; // Busca logged.html en templates
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes)
    {
        session.removeAttribute("usuario");
        redirectAttributes.addFlashAttribute("message", "Has cerrado sesión correctamente");
        
        return "redirect:/login";
    }
}