package edu.mondragon.webengl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.user.model.RecienLLegado;
import edu.mondragon.webengl.domain.user.model.User;
import edu.mondragon.webengl.domain.user.model.Voluntario;
import edu.mondragon.webengl.domain.user.service.UserService;
//import edu.mondragon.webengl.helper.ControllerHelper;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/create")
    public String showUserForm() {
        return "userForm"; // muestra el form.html
    }

    @PostMapping("/create")
    public String createUser(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String tipo,
        @RequestParam String first_name,
        @RequestParam String second_name,
        @RequestParam String email,
        @RequestParam(required = false) String comunidad_autonoma,
        @RequestParam(required = false) String ciudad,
        @RequestParam(required = false) String pais,
        @RequestParam(required = false) String idioma_principal,
        HttpSession session,
        RedirectAttributes redirectAttributes,
            org.springframework.ui.Model model // <-- Añade Model aquí

    ) {

        if (userService.existsByUsername(username)) {
           // redirectAttributes.addFlashAttribute("error", "El usuario ya existe.");
                   model.addAttribute("error", "El usuario ya existe.");
            return "userForm"; // redirige a userForm.html
            //return "redirect:/error?vista=userForm&mensaje=El+usuario+ya+existe";
        }

        User user;
        if ("voluntario".equalsIgnoreCase(tipo)) {
            Voluntario vol = new Voluntario();
            vol.setComunidad_autonoma(comunidad_autonoma);
            user = vol;
        } else if ("recienllegado".equalsIgnoreCase(tipo)) {
            RecienLLegado rec = new RecienLLegado();
            rec.setCiudad(ciudad);
            rec.setPais(pais);
            rec.setLang(idioma_principal != null ? java.util.Locale.forLanguageTag(idioma_principal) : null);
            user = rec;
        } else {
            user = new User();
            
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(first_name);
        user.setSecondName(second_name);
        user.setEmail(email);
        user.setTipo(tipo);

        userService.saveUser(user);

        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("message", "Usuario creado correctamente.");
        return "login";
    }
    /*
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("message", "message.logout");
        return "redirect:/login";
    }
        */
}



