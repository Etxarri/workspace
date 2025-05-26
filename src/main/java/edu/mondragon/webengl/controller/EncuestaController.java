package edu.mondragon.webengl.controller;

import edu.mondragon.webengl.domain.user.model.Encuesta;
import edu.mondragon.webengl.domain.user.service.EncuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class EncuestaController
{
    @Autowired
    private EncuestaService encuestaService;

    @GetMapping("/encuesta")
    public String mostrarFormulario(Model model)
    {
        model.addAttribute("encuesta", new Encuesta());
        return "encuesta";
    }

    @PostMapping("/encuesta")
    public String procesarEncuesta(@ModelAttribute Encuesta encuesta)
    {
        encuestaService.guardarEncuesta(encuesta);
        return "redirect:/gracias";
    }

    @GetMapping("/gracias")
    public String mostrarGracias()
    {
        return "gracias";
    }
}