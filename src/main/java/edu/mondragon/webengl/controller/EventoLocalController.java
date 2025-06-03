package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.categoria.repository.CategoriaRepository;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.model.RecienllegadoApuntarseEvento;
import edu.mondragon.webengl.domain.evento.model.RecienllegadoEventoId;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import edu.mondragon.webengl.domain.evento.repository.RecienllegadoApuntarseEventoRepository;
import edu.mondragon.webengl.domain.user.model.Usuario.TipoUsuario;
import edu.mondragon.webengl.domain.user.repository.RecienllegadoRepository;
import edu.mondragon.webengl.seguridad.UsuarioDetails;

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
    private final CategoriaRepository categoriaRepository;

    public EventoLocalController(EventoLocalRepository eventoRepo, RecienllegadoApuntarseEventoRepository inscripcionRepo, RecienllegadoRepository recienllegadoRepo, CategoriaRepository categoriaRepository) {
        this.eventoRepo = eventoRepo;
        this.inscripcionRepo = inscripcionRepo;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/listaEventos")
    public String listarEventosLocales(
        Model model,
        @AuthenticationPrincipal UsuarioDetails usuario,
        @RequestParam(value = "categoria", required = false) Integer categoriaID) {

        int comunidadId = usuario.getUsuario().getCiudad().getComunidadAutonoma();

        List<EventoLocal> eventos;
        if (categoriaID != null && categoriaID != 0) {
            eventos = eventoRepo.findByCiudad_ComunidadAutonoma_ComunidadIDAndCategoria_CategoriaID(comunidadId, categoriaID);
        } else {
            eventos = eventoRepo.findByCiudad_ComunidadAutonoma_ComunidadID(comunidadId);
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("eventos", eventos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaID);
        return "evento/listaEventos";
    }

        // Mostrar todos los recursos
    @GetMapping
    public String listarEventos(Model model) {
        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        return "evento/listaEventos"; // nombre del template Thymeleaf
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
    public String apuntarseEvento(
            @PathVariable("eventoID") int eventoID,
            @AuthenticationPrincipal UsuarioDetails user,
            HttpSession session,
            RedirectAttributes redirectAttrs,
            Model model) { // <-- Añade Model aquí

        if (!user.getUsuario().getTipo().equals(TipoUsuario.recienllegado)) {
            redirectAttrs.addFlashAttribute("error", "Solo los recién llegados pueden apuntarse.");
            System.out.println("Usuario no es recienllegado: " + user.getUsuario().getTipo());
            return "redirect:/eventos/listaEventos";
        }

        RecienllegadoEventoId compuesta = new RecienllegadoEventoId(user.getUsuario().getUsuarioID(), eventoID);

        if (inscripcionRepo.existsById(compuesta)) {
            redirectAttrs.addFlashAttribute("info", "Ya estás apuntado a este evento.");
            System.out.println("Ya está apuntado al evento: " + eventoID);
            return "redirect:/eventos/listaEventos";
        } else {
            RecienllegadoApuntarseEvento inscripcion = new RecienllegadoApuntarseEvento();
            inscripcion.setId(compuesta);
            inscripcion.setFechaInscripcion(LocalDate.now());
            inscripcionRepo.save(inscripcion);

            // Cargar el evento y pasarlo al modelo
            Optional<EventoLocal> eventoOpt = eventoRepo.findById(eventoID);
            eventoOpt.ifPresent(evento -> model.addAttribute("evento", evento));

            System.out.println("Apuntado al evento: " + eventoID);
            return "evento/apuntado";
        }
    }

    @GetMapping("/misEventos")
    public String misEventos(
            @AuthenticationPrincipal UsuarioDetails user,
            Model model,
            @RequestParam(value = "categoria", required = false) Integer categoriaID) {
        int usuarioId = user.getUsuario().getUsuarioID();
        List<RecienllegadoApuntarseEvento> inscripciones = inscripcionRepo.findById_UsuarioID(usuarioId);
        List<EventoLocal> eventos = inscripciones.stream()
                .map(insc -> eventoRepo.findById(insc.getId().getEventoID()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        // Filtrar por categoría si se selecciona una
        if (categoriaID != null && categoriaID != 0) {
            eventos = eventos.stream()
                    .filter(e -> e.getCategoria().getCategoriaID() == categoriaID)
                    .toList();
        }
        
        model.addAttribute("eventos", eventos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaID); // Para que la vista sepa cuál está seleccionada
        return "evento/misEventos";
    }

    @GetMapping("/{eventoID}/desapuntarse")
    public String mostrarDesapuntarse(
            @PathVariable("eventoID") int eventoID,
            @AuthenticationPrincipal UsuarioDetails user,
            Model model) {
        Optional<EventoLocal> eventoOpt = eventoRepo.findById(eventoID);
        if (eventoOpt.isPresent()) {
            model.addAttribute("evento", eventoOpt.get());
            return "evento/desapuntarse";
        } else {
            return "redirect:/eventos/misEventos";
        }
    }

    @PostMapping("/{eventoID}/desapuntarse")
    public String desapuntarseEvento(
            @PathVariable("eventoID") int eventoID,
            @AuthenticationPrincipal UsuarioDetails user,
            RedirectAttributes redirectAttrs,
            Model model) { // Añade Model aquí
        int usuarioId = user.getUsuario().getUsuarioID();
        RecienllegadoEventoId compuesta = new RecienllegadoEventoId(usuarioId, eventoID);

        if (inscripcionRepo.existsById(compuesta)) {
            inscripcionRepo.deleteById(compuesta);
            // Cargar el evento y pasarlo al modelo
            Optional<EventoLocal> eventoOpt = eventoRepo.findById(eventoID);
            eventoOpt.ifPresent(evento -> model.addAttribute("evento", evento));
            return "evento/desapuntado";
        } else {
            redirectAttrs.addFlashAttribute("error", "No estabas apuntado a este evento.");
            return "redirect:/eventos/misEventos";
        }
    }
}

