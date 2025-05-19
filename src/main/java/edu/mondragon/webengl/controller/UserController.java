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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public String showUserForm() {
        return "userForm"; // muestra el form.html
    }

    @PostMapping
    public String createUser(@RequestParam String username, 
                        @RequestParam String password,
                        HttpSession session, 
                        RedirectAttributes redirectAttributes) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);

        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("message", "message.login");
        return "login"; // redirige a login

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



