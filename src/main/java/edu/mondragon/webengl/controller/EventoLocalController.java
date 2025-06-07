package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.categoria.model.Categoria;
import edu.mondragon.webengl.domain.categoria.repository.CategoriaRepository;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.model.UsuarioEventoId;
import edu.mondragon.webengl.domain.evento.model.UsuarioApuntarseEvento;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import edu.mondragon.webengl.domain.evento.repository.UsuarioApuntarseEventoRepository;
import edu.mondragon.webengl.domain.pais.repository.CiudadRepository;
import edu.mondragon.webengl.domain.user.model.Voluntario;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.model.Usuario.TipoUsuario;
import edu.mondragon.webengl.domain.user.repository.RecienllegadoRepository;
import edu.mondragon.webengl.domain.user.repository.VoluntarioRepository;
import edu.mondragon.webengl.seguridad.UsuarioDetails;
import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.categoria.model.Categoria;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/eventos")
public class EventoLocalController {

    /* FALTA POR HACER:
        Vistas eventos/lista.html y eventos/detalle.html (si quieres te ayudo con eso)
        Control de acceso para que solo se apunten Recienllegado
        Método getUsuarioID() en Recienllegado
     */

    private final EventoLocalRepository eventoRepo;
    private final UsuarioApuntarseEventoRepository inscripcionRepo;
    private final CategoriaRepository categoriaRepository;
    private final VoluntarioRepository voluntarioRepo;
    private final CiudadRepository ciudadRepository;
    private final CategoriaRepository categoriaRepo;

    private static final Logger logger = LoggerFactory.getLogger(EventoLocalController.class);

    public EventoLocalController(EventoLocalRepository eventoRepo, UsuarioApuntarseEventoRepository inscripcionRepo, RecienllegadoRepository recienllegadoRepo, CategoriaRepository categoriaRepository, VoluntarioRepository voluntarioRepo, CiudadRepository ciudadRepository, CategoriaRepository categoriaRepo) {
        this.eventoRepo = eventoRepo;
        this.inscripcionRepo = inscripcionRepo;
        this.categoriaRepository = categoriaRepository;
        this.voluntarioRepo = voluntarioRepo;
        this.ciudadRepository = ciudadRepository;
        this.categoriaRepo = categoriaRepo;
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
        model.addAttribute("paginaActual", "listaEventos");


        int usuarioId = usuario.getUsuario().getUsuarioID();
        List<UsuarioApuntarseEvento> inscripciones = inscripcionRepo.findById_UsuarioID(usuarioId);
        List<Integer> eventosApuntadosIds = inscripciones.stream()
                .map(insc -> insc.getId().getEventoID())
                .toList();

        List<EventoLocal> eventosFiltrados = eventos.stream()
                .filter(e -> !eventosApuntadosIds.contains(e.getEventoID()))
                .filter(e -> e.getUsuario() == null || e.getUsuario().getUsuarioID() != usuarioId) // <-- Añadido
                .toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("eventos", eventosFiltrados);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaID);
        return "evento/listaEventos";
    }

    @GetMapping
    public String listarEventos(Model model) {
        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "evento/listaEventos"; // nombre del template Thymeleaf
    }

    @GetMapping("/{eventoID}")
    public String detalleEvento(@PathVariable("eventoID") int eventoID, Model model, @AuthenticationPrincipal UsuarioDetails usuario) {
        Optional<EventoLocal> eventoOpt = eventoRepo.findById(eventoID);
        model.addAttribute("paginaActual", "listaEventos");

        Usuario loggedUser = usuario.getUsuario();
        if(inscripcionRepo.existsById_UsuarioIDAndId_EventoID(loggedUser.getUsuarioID(), eventoID)){
            model.addAttribute("apuntado", true);
        } else {
            model.addAttribute("apuntado", false);  
        }

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
            @RequestParam(value = "redirectTo", required = false) String redirectTo,
            HttpSession session,
            RedirectAttributes redirectAttrs,
            Model model) {

        if (!(user.getUsuario().getTipo().equals(TipoUsuario.recienllegado) || user.getUsuario().getTipo().equals(TipoUsuario.voluntario))) {
            redirectAttrs.addFlashAttribute("error", "Solo los recién llegados y voluntarios pueden apuntarse.");
            return "redirect:/eventos/listaEventos";
        }

        UsuarioEventoId compuesta = new UsuarioEventoId(user.getUsuario().getUsuarioID(), eventoID);

        if (inscripcionRepo.existsById(compuesta)) {
            redirectAttrs.addFlashAttribute("info", "Ya estás apuntado a este evento.");
            return "redirect:/eventos/listaEventos";
        } else {
            UsuarioApuntarseEvento inscripcion = new UsuarioApuntarseEvento();
            inscripcion.setId(compuesta);
            inscripcion.setFechaInscripcion(LocalDate.now());
            inscripcionRepo.save(inscripcion);

            // Redirección según el parámetro
            if ("listaEventos".equals(redirectTo)) {
                return "redirect:/eventos/listaEventos";
            } else if ("misEventos".equals(redirectTo)) {
                return "redirect:/eventos/misEventos";
            } else {
                return "redirect:/eventos/" + eventoID;
            }
        }
    }

    @GetMapping("/misEventos")
    public String misEventos(
            @AuthenticationPrincipal UsuarioDetails user,
            Model model,
            @RequestParam(value = "categoria", required = false) Integer categoriaID) {
        int usuarioId = user.getUsuario().getUsuarioID();
        List<UsuarioApuntarseEvento> inscripciones = inscripcionRepo.findById_UsuarioID(usuarioId);
        List<EventoLocal> eventos = inscripciones.stream()
                .map(insc -> eventoRepo.findById(insc.getId().getEventoID()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if (categoriaID != null && categoriaID != 0) {
            eventos = eventos.stream()
                    .filter(e -> e.getCategoria().getCategoriaID() == categoriaID)
                    .toList();
        }
        logger.info("\n\n\nTipo de usuario: {}", user.getUsuario().getTipo());

        model.addAttribute("paginaActual", "misEventos");

        List<Categoria> categorias = categoriaRepository.findAll();
        System.out.println("CATEGORIAS DISPONIBLES: " + categorias.size()); // <-- Línea de depuración


        model.addAttribute("eventos", eventos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaID);
        model.addAttribute("usuario", user.getUsuario());   
        return "evento/misEventos";
    }


    @PostMapping("/{eventoID}/desapuntarse")
    public String desapuntarseEvento(
            @PathVariable("eventoID") int eventoID,
            @AuthenticationPrincipal UsuarioDetails user,
            @RequestParam(value = "redirectTo", required = false) String redirectTo,
            RedirectAttributes redirectAttrs,
            Model model) { 
        int usuarioId = user.getUsuario().getUsuarioID();
        UsuarioEventoId compuesta = new UsuarioEventoId(usuarioId, eventoID);

        model.addAttribute("paginaActual", "listaEventos");

        if (inscripcionRepo.existsById(compuesta)) {
            inscripcionRepo.deleteById(compuesta);

            // Redirección según el parámetro
            if ("listaEventos".equals(redirectTo)) {
                return "redirect:/eventos/listaEventos";
            } else if ("misEventos".equals(redirectTo)) {
                return "redirect:/eventos/misEventos";
            } else {
                return "redirect:/eventos/" + eventoID;
            }
        } else {
            redirectAttrs.addFlashAttribute("error", "No estabas apuntado a este evento.");
            return "redirect:/eventos/misEventos";
        }
    }

    @GetMapping("/crearEvento")
    public String crearEvento(@AuthenticationPrincipal UsuarioDetails user,
                            Model model) {
        if (!user.getUsuario().getTipo().equals(TipoUsuario.voluntario)) {
            model.addAttribute("error", "Solo los voluntarios pueden crear eventos.");
            return "redirect:/eventos/listaEventos";
        }
        model.addAttribute("paginaActual", "listaEventos");
        model.addAttribute("ciudades", ciudadRepository.findAll());
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("usuario", user.getUsuario());
        return "evento/crearEvento";
    }

    @PostMapping("/crearEvento")
    public String crearEvento(@AuthenticationPrincipal UsuarioDetails user,
                            @ModelAttribute EventoLocal evento,
                            @RequestParam("ciudadID") int ciudadID,
                            @RequestParam("categoriaID") int categoriaID,
                            RedirectAttributes redirectAttrs) {
        if (!user.getUsuario().getTipo().equals(TipoUsuario.voluntario)) {
            redirectAttrs.addFlashAttribute("error", "Solo los voluntarios pueden crear eventos.");
            return "redirect:/eventos/listaEventos";
        }

        Ciudad ciudad = ciudadRepository.findById(ciudadID)
            .orElseThrow(() -> new IllegalArgumentException("Ciudad no encontrada"));
        evento.setCiudad(ciudad);

        Categoria categoria = categoriaRepo.findById(categoriaID)
            .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        evento.setCategoria(categoria);

        Voluntario userVoluntario = voluntarioRepo.findById(user.getUsuario().getUsuarioID())
                .orElseThrow(() -> new IllegalArgumentException("El usuario no es un voluntario válido."));
        evento.setUsuario(userVoluntario);
        eventoRepo.save(evento);

        redirectAttrs.addFlashAttribute("success", "Evento creado correctamente.");
        return "redirect:/eventos/misEventos";
    }
}

