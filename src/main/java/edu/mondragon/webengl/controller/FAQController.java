package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.foro.model.PreguntaFrecuente;
import edu.mondragon.webengl.domain.foro.repository.PreguntaFrecuenteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/faq")
public class FAQController {

/*VISTAS SUGERIDAS::
    1. faq/lista.html: muestra todas las preguntas y respuestas.
    2. faq/detalle.html: muestra una pregunta en detalle (opcional).
    3. faq/formulario.html: formulario para agregar nuevas preguntas (si aplica).
*/

    private final PreguntaFrecuenteRepository faqRepository;

    public FAQController(PreguntaFrecuenteRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    // Mostrar todas las preguntas frecuentes
    @GetMapping
    public String mostrarFAQ(Model model) {
        List<PreguntaFrecuente> preguntas = faqRepository.findAll();
        model.addAttribute("preguntas", preguntas);
        return "faq/lista"; // Vista Thymeleaf para listar FAQ
    }

    // Mostrar detalle de una pregunta (opcional)
    @GetMapping("/{id}")
    public String detallePregunta(@PathVariable int id, Model model) {
        PreguntaFrecuente pregunta = faqRepository.findById(id).orElse(null);
        if (pregunta == null) {
            model.addAttribute("error", "Pregunta no encontrada");
            return "faq/lista";
        }
        model.addAttribute("pregunta", pregunta);
        return "faq/detalle"; // Vista para mostrar una pregunta individual
    }

    // Crear nuevas preguntas frecuentes (opcional, solo si hay panel de administración)
    @GetMapping("/crear")
    public String nuevaPreguntaForm(Model model) {
        model.addAttribute("pregunta", new PreguntaFrecuente());
        return "faq/formulario";
    }

    @PostMapping("/crear")
    public String guardarPregunta(@ModelAttribute PreguntaFrecuente pregunta) {
        faqRepository.save(pregunta);
        return "redirect:/faq";
    }
}

