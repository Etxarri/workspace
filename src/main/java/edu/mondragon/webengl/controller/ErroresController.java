package edu.mondragon.webengl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErroresController {

    @GetMapping("/error")
  /*  public String mostrarError(@RequestParam(required = false) String mensaje, Model model) {
        model.addAttribute("mensaje", mensaje != null ? mensaje : "Ha ocurrido un error inesperado.");
        return "error"; // Muestra la plantilla error.html
    }
        */
      

    public String mostrarError(@RequestParam String vista, @RequestParam(required = false) 
    String mensaje, Model model) {
        model.addAttribute("error", mensaje != null ? mensaje : "Ha ocurrido un error inesperado.");
        return vista; // Renderiza la vista original con el mensaje de error
    }
}