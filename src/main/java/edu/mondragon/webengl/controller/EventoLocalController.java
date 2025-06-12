package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.categoria.model.Categoria;
import edu.mondragon.webengl.domain.categoria.repository.CategoriaRepository;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import edu.mondragon.webengl.domain.pais.repository.CiudadRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.model.Usuario.TipoUsuario;
import edu.mondragon.webengl.domain.user.repository.RecienllegadoRepository;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import edu.mondragon.webengl.seguridad.UsuarioDetails;
import edu.mondragon.webengl.domain.pais.model.Ciudad;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    private final CategoriaRepository categoriaRepository;
    private final CiudadRepository ciudadRepository;
    private final CategoriaRepository categoriaRepo;
    private final UsuarioRepository usuarioRepo;


    public EventoLocalController(EventoLocalRepository eventoRepo, RecienllegadoRepository recienllegadoRepo, CategoriaRepository categoriaRepository, CiudadRepository ciudadRepository, CategoriaRepository categoriaRepo, UsuarioRepository usuarioRepo) {
        this.eventoRepo = eventoRepo;
        this.categoriaRepository = categoriaRepository;
        this.ciudadRepository = ciudadRepository;
        this.categoriaRepo = categoriaRepo;
        this.usuarioRepo = usuarioRepo;
    }
    private static final Logger logger = LoggerFactory.getLogger(EventoLocalController.class);
    @GetMapping("/listaEventos")
    public String listarEventosLocales(
        Model model,
        @AuthenticationPrincipal UsuarioDetails user,
        @RequestParam(value = "categoria", required = false) Integer categoriaID) {

        // Recarga el usuario desde la base de datos para tener los eventos apuntados actualizados
        Usuario usuario = usuarioRepo.findById(user.getUsuario().getUsuarioID()).orElseThrow();
        usuario.getEventosApuntados().size(); // Fuerza la carga

        int comunidadId = usuario.getCiudad().getComunidadAutonoma();

        List<EventoLocal> eventos;
        if (categoriaID != null && categoriaID != 0) {
            eventos = eventoRepo.findByCiudad_ComunidadAutonoma_ComunidadIDAndCategoria_CategoriaID(comunidadId, categoriaID);
        } else {
            eventos = eventoRepo.findByCiudad_ComunidadAutonoma_ComunidadID(comunidadId);
        }
        model.addAttribute("paginaActual", "listaEventos");

        int usuarioId = usuario.getUsuarioID();

        List<EventoLocal> eventosApuntados = new java.util.ArrayList<>(usuario.getEventosApuntados());

        List<EventoLocal> eventosFiltrados = eventos.stream()
                .filter(e -> !eventosApuntados.contains(e))
                .filter(e -> e.getUsuario() == null || e.getUsuario().getUsuarioID() != usuarioId)
                .toList();

        model.addAttribute("usuario", usuario);
        model.addAttribute("eventos", eventosFiltrados);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaID);

        model.addAttribute("tituloPagina", "Eventos locales");
        model.addAttribute("textoNoEventos", "No hay eventos disponibles para esta categoría.");
        model.addAttribute("tituloPaginaKey", "home"); // o "my_events" según la página

        return "evento/eventosListaGenerica";
    }
    /*
    @GetMapping
    public String listarEventos(Model model) {
        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "evento/listaEventos"; // nombre del template Thymeleaf
    }
        */

    @GetMapping("/{eventoID}")
    public String detalleEvento(@PathVariable("eventoID") int eventoID, Model model, @AuthenticationPrincipal UsuarioDetails usuario) {
        Optional<EventoLocal> eventoOpt = eventoRepo.findById(eventoID);

        Usuario loggedUser = usuario.getUsuario();
        boolean existe = usuarioRepo.existsByUsuarioIDAndEventosApuntados_EventoID(loggedUser.getUsuarioID(), eventoID);

        logger.info("Usuario ID: " + loggedUser.getUsuarioID() + ", Evento ID: " + eventoID + ", Existe: " + existe);
        if(existe){
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

        int usuarioId = user.getUsuario().getUsuarioID();
        Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow();
        EventoLocal evento = eventoRepo.findById(eventoID).orElseThrow();

        if(usuarioRepo.existsByUsuarioIDAndEventosApuntados_EventoID(usuarioId, eventoID)) {
            redirectAttrs.addFlashAttribute("info", "Ya estás apuntado a este evento.");
            return "redirect:/eventos/misEventos";
        } else {
            usuario.getEventosApuntados().add(evento);
            evento.getUsuariosApuntados().add(usuario);
            usuarioRepo.save(usuario); // Solo guarda el usuario

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
        
        Usuario usuario = usuarioRepo.findById(user.getUsuario().getUsuarioID()).orElseThrow();

        // Eventos a los que está apuntado
        List<EventoLocal> eventosApuntados = new java.util.ArrayList<>(usuario.getEventosApuntados());

        // Eventos creados por el usuario
        List<EventoLocal> eventosCreados = eventoRepo.findByUsuario_UsuarioID(usuario.getUsuarioID());

        // Unir ambos (sin duplicados)
        java.util.Set<EventoLocal> eventos = new java.util.HashSet<>(eventosApuntados);
        eventos.addAll(eventosCreados);

        logger.info("\nUsuario ID: " + usuario.getEventosApuntados() + "\n");

        // Filtrar por categoría si corresponde
        List<EventoLocal> eventosFiltrados = new java.util.ArrayList<>(eventos);
        if (categoriaID != null && categoriaID != 0) {
            eventosFiltrados = eventosFiltrados.stream()
                .filter(e -> e.getCategoria().getCategoriaID() == categoriaID)
                .toList();
        }

        model.addAttribute("paginaActual", "misEventos");
        model.addAttribute("eventos", eventosFiltrados);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaID);
        model.addAttribute("usuario", usuario);
        model.addAttribute("tituloPagina", "Mis eventos");
        model.addAttribute("textoNoEventos", "No estás apuntado ni has creado ningún evento.");
        model.addAttribute("tituloPaginaKey", "my_events"); // o "home" según la página

        return "evento/eventosListaGenerica";
    }


    @PostMapping("/{eventoID}/desapuntarse")
    public String desapuntarseEvento(
            @PathVariable("eventoID") int eventoID,
            @AuthenticationPrincipal UsuarioDetails user,
            @RequestParam(value = "redirectTo", required = false) String redirectTo,
            HttpSession session,
            RedirectAttributes redirectAttrs,
            Model model) {

        int usuarioId = user.getUsuario().getUsuarioID();
        Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow();
        EventoLocal evento = eventoRepo.findById(eventoID).orElseThrow();

        if(usuarioRepo.existsByUsuarioIDAndEventosApuntados_EventoID(usuarioId, eventoID)) {
            usuario.getEventosApuntados().remove(evento);
            evento.getUsuariosApuntados().remove(usuario);
            usuarioRepo.save(usuario);

            if ("listaEventos".equals(redirectTo)) {
                return "redirect:/eventos/listaEventos";
            } else if ("misEventos".equals(redirectTo)) {
                return "redirect:/eventos/misEventos";
            } else {
                return "redirect:/eventos/" + eventoID;
            }
        }
        else {
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

        evento.setUsuario(user.getUsuario());
        eventoRepo.save(evento);

        redirectAttrs.addFlashAttribute("success", "Evento creado correctamente.");
        return "redirect:/eventos/misEventos";
    }
}

