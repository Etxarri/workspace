package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.encuesta.model.Encuesta;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuesta;
import edu.mondragon.webengl.domain.encuesta.repository.EncuestaRepository;
import edu.mondragon.webengl.domain.encuesta.repository.HacerEncuestaRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/encuestas")
public class EncuestaController {

    /* NOTAS del CHARLY:
     * La variable 'respuestas' es un ejemplo, normalmente tendrás que adaptar la forma de capturar y almacenar las respuestas (quizás usando un DTO o un formulario complejo).
     * Asegúrate de tener en tu entidad HacerEncuesta el campo para guardar esas respuestas.
     * Puedes implementar validaciones para evitar respuestas repetidas por usuario.
     */

    private final EncuestaRepository encuestaRepo;
    private final HacerEncuestaRepository hacerEncuestaRepo;

    public EncuestaController(EncuestaRepository encuestaRepo, HacerEncuestaRepository hacerEncuestaRepo) {
        this.encuestaRepo = encuestaRepo;
        this.hacerEncuestaRepo = hacerEncuestaRepo;
    }

    // Lista todas las encuestas disponibles
    @GetMapping
    public String listarEncuestas(Model model) {
        List<Encuesta> encuestas = encuestaRepo.findAll();
        model.addAttribute("encuestas", encuestas);
        return "encuestas/lista";
    }

    // Mostrar formulario para responder encuesta
    @GetMapping("/{id}")
    public String mostrarEncuesta(@PathVariable int id, Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        Optional<Encuesta> encuestaOpt = encuestaRepo.findById(id);
        if (encuestaOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Encuesta no encontrada");
            return "redirect:/encuestas";
        }

        Usuario usuario = (Usuario) session.getAttribute("user");
        if (usuario == null) {
            redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión para responder la encuesta");
            return "redirect:/login";
        }

        // Aquí podrías añadir lógica para evitar que un usuario conteste dos veces la misma encuesta si quieres

        model.addAttribute("encuesta", encuestaOpt.get());
        return "encuestas/responder";
    }

    // Procesar respuestas de la encuesta
    @PostMapping("/{id}/responder")
    public String guardarRespuesta(@PathVariable int id,
                                   @RequestParam String respuestas, // ajusta según tus datos, por ejemplo JSON o params separados
                                   HttpSession session,
                                   RedirectAttributes redirectAttrs) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        if (usuario == null) {
            redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión para responder la encuesta");
            return "redirect:/login";
        }

        Optional<Encuesta> encuestaOpt = encuestaRepo.findById(id);
        if (encuestaOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Encuesta no encontrada");
            return "redirect:/encuestas";
        }

        // Aquí guardamos la respuesta (simplificado)
        HacerEncuesta he = new HacerEncuesta();
        he.setEncuesta(encuestaOpt.get());
        he.setUsuario(usuario);
        //he.setFecha(LocalDateTime.now());
        //he.setRespuestas(respuestas); // campo ejemplo, ajusta según tu modelo

        hacerEncuestaRepo.save(he);

        redirectAttrs.addFlashAttribute("mensaje", "Gracias por responder la encuesta");
        return "redirect:/encuestas";
    }
}

