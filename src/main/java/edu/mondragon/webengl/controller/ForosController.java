package edu.mondragon.webengl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ForosController {
    @GetMapping("/forosPrincipal")
    public String mostrarForosPrincipal() {
        return "foros/forosPrincipal";
    }

    @GetMapping("/foros/{foro}")
    public String mostrarForoSecundario(@PathVariable String foro, Model model) {
        model.addAttribute("foro", foro);
        // Puedes añadir más atributos según el foro si quieres
        return "foros/forosSecundario";
    }
    
}