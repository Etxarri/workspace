package edu.mondragon.webengl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.user.model.User;
import edu.mondragon.webengl.domain.user.service.UserService;
//import edu.mondragon.webengl.helper.ControllerHelper;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public String showLoginForm() {
        return "login"; // muestra el login.html
    }

    @PostMapping
    public String login(@RequestParam String username, 
                        @RequestParam String password,
                        HttpSession session, 
                        RedirectAttributes redirectAttributes,org.springframework.ui.Model model ) {
        User user = userService.login(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("message", "message.login");
            //System.out.println("Ha entrado el usuario: " + user.getUsername() + " con la contraseña: " + user.getPassword());
            return "logged"; // redirige a logged.html
        } else {
            //System.out.println("Error al entrar el usuario: " + username + " con la contraseña: " + password);
            model.addAttribute("error", "Error al iniciar sesión. Usuario o contraseña incorrectos.");
           // redirectAttributes.addFlashAttribute("error", "Error al iniciar sesión. Usuario o contraseña incorrectos.");
            return "login";

        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("message", "message.logout");
        return "redirect:/login";
    }
}


