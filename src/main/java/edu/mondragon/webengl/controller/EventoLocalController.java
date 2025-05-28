package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.model.RecienllegadoApuntarseEvento;
import edu.mondragon.webengl.domain.evento.model.RecienllegadoEventoId;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import edu.mondragon.webengl.domain.evento.repository.RecienllegadoApuntarseEventoRepository;
import edu.mondragon.webengl.domain.user.model.RecienLLegado;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.RecienllegadoRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventos")
public class EventoLocalController {

    /* FALTA POR HACER:
        Vistas eventos/lista.html y eventos/detalle.html (si quieres te ayudo con eso)
        Control de acceso para que solo se apunten Recienllegado
        Método getUsuarioID() en Recienllegado
     */

    private final EventoLocalRepository eventoRepo;
    private final RecienllegadoApuntarseEventoRepository inscripcionRepo;
    private final RecienllegadoRepository recienllegadoRepo;
    public EventoLocalController(EventoLocalRepository eventoRepo, RecienllegadoApuntarseEventoRepository inscripcionRepo, RecienllegadoRepository recienllegadoRepo) {
        this.eventoRepo = eventoRepo;
        this.inscripcionRepo = inscripcionRepo;
        this.recienllegadoRepo = recienllegadoRepo;
    }

    @GetMapping
    public String listarEventos(Model model) {
        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        return "eventos/lista"; // Vista Thymeleaf con el listado
    }

    @GetMapping("/{eventoID}")
    public String detalleEvento(@PathVariable("eventoID") int eventoID, Model model) {
        Optional<EventoLocal> eventoOpt = eventoRepo.findById(eventoID);
        if (eventoOpt.isPresent()) {
            model.addAttribute("evento", eventoOpt.get());
            return "evento/evento";
        } else {
            return "redirect:/eventos";
        }
    }

    @PostMapping("/{eventoID}/apuntarse")
    public String apuntarseEvento(@PathVariable("eventoID") int eventoID, @AuthenticationPrincipal Usuario user, HttpSession session, RedirectAttributes redirectAttrs) {

        if (!user.getTipo().equals("recienllegado")) {
            redirectAttrs.addFlashAttribute("error", "Solo los recién llegados pueden apuntarse.");
            System.out.println("Usuario no es recienllegado: " + user.getTipo());
            return "redirect:/eventos";
        }
        Recienllegado recien = recienllegadoRepo.findById(user.getUsuarioID()).orElse(null);

        RecienllegadoEventoId compuesta = new RecienllegadoEventoId(recien.getUsuarioID(), eventoID);

        if (inscripcionRepo.existsById(compuesta)) {
            redirectAttrs.addFlashAttribute("info", "Ya estás apuntado a este evento.");
            System.out.println("Ya está apuntado al evento: " + eventoID);
        } else {
            RecienllegadoApuntarseEvento inscripcion = new RecienllegadoApuntarseEvento();
            inscripcion.setId(compuesta);
            inscripcion.setFechaInscripcion(LocalDate.now());
            inscripcionRepo.save(inscripcion);
            redirectAttrs.addFlashAttribute("mensaje", "Te has apuntado correctamente.");
            System.out.println("Apuntado al evento: " + eventoID);
        }

        return "redirect:/eventos/" + eventoID;
    }
}

