package edu.mondragon.webengl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "login"; // esto busca login.html en templates
    }
}
