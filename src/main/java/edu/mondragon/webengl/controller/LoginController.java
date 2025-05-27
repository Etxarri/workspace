package edu.mondragon.webengl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.service.UsuarioService;
//import edu.mondragon.webengl.helper.ControllerHelper;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UsuarioService userService;

    public LoginController(UsuarioService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        return "login"; // muestra el login.html
    }
/*
    @PostMapping
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, 
                        RedirectAttributes redirectAttributes) {
        Usuario user = userService.login(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("message", "message.login");
            System.out.println("Ha entrado el usuario: " + user.getUsername() + " con la contraseña: " + password);
            return "logged.html"; // redirige a logged.html
        } else {
            System.out.println("Error al entrar el usuario: " + username + " con la contraseña: " + password);
            redirectAttributes.addFlashAttribute("error", "Error al iniciar sesión. Verifica tus credenciales.");
            return "redirect:/login";
        }
    }
*/
    @GetMapping("/logged")
    public String logged() {
        return "logged"; // Busca logged.html en templates
    }
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("message", "message.logout");
        return "redirect:/login";
    }
}


