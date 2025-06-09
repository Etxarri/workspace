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

        Usuario usuario = user.getUsuario();
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

        List<EventoLocal> eventosFiltrados = eventos.stream()//.stream() señala que estamos trabajando con una lista
                .filter(e -> !eventosApuntados.contains(e))//Esto descarta los eventos a los que el usuario ya está apuntado.
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

        Usuario usuario = user.getUsuario();
        EventoLocal evento = eventoRepo.findById(eventoID).orElseThrow();

        if(usuarioRepo.existsByUsuarioIDAndEventosApuntados_EventoID(usuario.getUsuarioID(), eventoID)) {
            redirectAttrs.addFlashAttribute("info", "Ya estás apuntado a este evento.");
            return "redirect:/eventos/misEventos";
        } else {

            usuario.getEventosApuntados().add(evento);
            evento.getUsuariosApuntados().add(usuario);
            usuarioRepo.save(usuario);
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
        
        Usuario usuario = user.getUsuario();

        List<EventoLocal> eventos = new java.util.ArrayList<>(usuario.getEventosApuntados());

        if (categoriaID != null && categoriaID != 0) {
            eventos = eventoRepo.findByCategoria_CategoriaID(categoriaID);
        }

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

        Usuario usuario = user.getUsuario();
        int usuarioId = usuario.getUsuarioID();

        model.addAttribute("paginaActual", "listaEventos");
        if (usuarioRepo.existsByUsuarioIDAndEventosApuntados_EventoID(usuarioId, eventoID)) {
            usuario.getEventosApuntados().removeIf(e -> e.getEventoID() == eventoID);
            usuarioRepo.save(usuario);
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

        evento.setUsuario(user.getUsuario());
        eventoRepo.save(evento);

        redirectAttrs.addFlashAttribute("success", "Evento creado correctamente.");
        return "redirect:/eventos/misEventos";
    }
}

