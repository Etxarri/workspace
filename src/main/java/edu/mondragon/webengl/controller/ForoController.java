package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;
import edu.mondragon.webengl.domain.foro.model.PreguntaFrecuente;
import edu.mondragon.webengl.domain.foro.model.Temaforo;
import edu.mondragon.webengl.domain.foro.repository.ComentarioForoRepository;
import edu.mondragon.webengl.domain.foro.repository.HiloforoRepository;
import edu.mondragon.webengl.domain.foro.repository.PreguntaFrecuenteRepository;
import edu.mondragon.webengl.domain.foro.repository.TemaforoRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.service.UsuarioService;
import edu.mondragon.webengl.seguridad.UsuarioDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/foro")
public class ForoController {

    private final HiloforoRepository hiloRepo;
    private final ComentarioForoRepository comentarioRepo;
    private final TemaforoRepository temaRepo;
    private final PreguntaFrecuenteRepository preguntaFrecuenteRepo;

    private final UsuarioService usuarioService;

    private static final Logger logger = LoggerFactory.getLogger(ForoController.class);


    public ForoController(HiloforoRepository hiloRepo, ComentarioForoRepository comentarioRepo, TemaforoRepository temaRepo, UsuarioService usuarioService, PreguntaFrecuenteRepository preguntaFrecuenteRepo) {
        this.hiloRepo = hiloRepo;
        this.comentarioRepo = comentarioRepo;
        this.temaRepo = temaRepo;
        this.usuarioService = usuarioService;
        this.preguntaFrecuenteRepo = preguntaFrecuenteRepo;
    }
    @GetMapping("/principal")
    public String principal(Model model) {
        List<Temaforo> temas = temaRepo.findAll();
        model.addAttribute("temaForo", temas);
        return "foros/forosPrincipal"; // Busca forosPrincipal.html en templates
    }

    @GetMapping("/{temaID}")
    public String foroPorTema(@PathVariable int temaID, Model model, HttpSession session) {
        Optional<Temaforo> temaOpt = temaRepo.findById(temaID);
        logger.info("\n\nAccediendo al foro del tema: {}", temaID);

        if (temaOpt.isPresent()) {
            Object temaEnSesion = session.getAttribute("tema");
            if (temaEnSesion == null || !temaEnSesion.equals(temaOpt.get())) {
                session.setAttribute("tema", temaOpt.get());
            }
            model.addAttribute("tema", temaOpt.get()); // <-- Añade esto
            model.addAttribute("preguntasUsuarios", preguntaFrecuenteRepo.findByTema_TemaID(temaID)); // Añade la pregunta si existe

            return "foros/forosSecundario";
        } else {
            return "redirect:/foros/forosPrincipal"; 
        }
    }

    @GetMapping("/preguntaUsuario/{preguntaID}")
    public String accederPreguntaUsuario(@PathVariable int preguntaID, Model model) {
        
        Optional<PreguntaFrecuente> preguntaOpt = preguntaFrecuenteRepo.findById(preguntaID);
        logger.info("\n\nAccediendo a la pregunta frecuente: {}\n\n", preguntaID);
        if (preguntaOpt.isPresent()) {
            Optional<Hiloforo> hiloOpt = hiloRepo.findByPreguntaID(preguntaID);
            if (hiloOpt.isPresent()) {
                Hiloforo hilo = hiloOpt.get();
                List<ComentarioForo> comentarios = comentarioRepo.findByHilo(hilo);
                model.addAttribute("hilo", hilo);
                model.addAttribute("comentarios", comentarios);
                model.addAttribute("pregunta", preguntaOpt.get()); // Añade la pregunta si existe
            }
            return "foros/pregunta";
        }
        return "redirect:/foro";
    }

    @PostMapping("/preguntasFrecuentes")
    public String crearPreguntaFrecuente(@RequestParam String contenido,
                                 RedirectAttributes redirectAttrs, HttpSession session, Model model) {

        Object tema = session.getAttribute("tema");
        if (tema == null) {
            redirectAttrs.addFlashAttribute("error", "Tema no válido.");
            return "redirect:/foro";
        }

        PreguntaFrecuente nuevaPregunta = new PreguntaFrecuente();
        nuevaPregunta.setPregunta(contenido);
        nuevaPregunta.setTema((Temaforo) tema);
        nuevaPregunta.setFechaCreacion(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        nuevaPregunta = preguntaFrecuenteRepo.saveAndFlush(nuevaPregunta);//Forzar persistencia inmediata 

        // Crear el hilo asociado a la nueva pregunta
        Hiloforo nuevoHilo = new Hiloforo();
        nuevoHilo.setPreguntaID(nuevaPregunta.getPreguntaID());
        nuevoHilo.setFechaCreacion(LocalDateTime.now());
        hiloRepo.save(nuevoHilo);

        // Recupera las preguntas y pásalas al modelo
        List<PreguntaFrecuente> preguntas = preguntaFrecuenteRepo.findByTema_TemaID(((Temaforo) tema).getTemaID());
        model.addAttribute("preguntasUsuarios", preguntas);
        model.addAttribute("tema", tema);

        return "foros/forosSecundario";
    }

        @PostMapping("/{hiloID}/{preguntaID}/comentario")
    public String agregarComentario(@PathVariable int hiloID,
                                    @PathVariable int preguntaID,
                                    @RequestParam String contenido,
                                    HttpSession session,
                                    @AuthenticationPrincipal UsuarioDetails user,
                                    RedirectAttributes redirectAttrs,
                                    Model model) {


        Optional<Hiloforo> hiloOpt = hiloRepo.findById(hiloID);
        if (hiloOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "El hilo no existe.");
            return "redirect:/foro";
        }

        ComentarioForo comentario = new ComentarioForo();
        comentario.setHilo(hiloOpt.get());
        comentario.setUsuario(usuarioService.findUsuarioByIdUsuario(user.getUsuario().getUsuarioID()));
        comentario.setContenido(contenido);
        comentario.setFechaHora(LocalDateTime.now());
        comentarioRepo.save(comentario);

        List<ComentarioForo> comentarios = comentarioRepo.findByHilo(hiloOpt.get());
        model.addAttribute("comentarios", comentarios);

        model.addAttribute("pregunta", preguntaFrecuenteRepo.findById(preguntaID).orElse(null));
        model.addAttribute("hilo", hiloOpt.get());
        return "/foros/pregunta";
    }

 

    @GetMapping
    public String listarHilos(@RequestParam(required = false) int temaId, Model model) {
        List<Hiloforo> hilos;
        if (temaId != 0) {
            Optional<Temaforo> temaOpt = temaRepo.findById(temaId);
            if (temaOpt.isPresent()) {
                model.addAttribute("temaSeleccionado", temaOpt.get());
            } else {
                hilos = hiloRepo.findAll();
            }
        } else {
            hilos = hiloRepo.findAll();
        }
        List<Temaforo> temas = temaRepo.findAll();
        model.addAttribute("temas", temas);
        //model.addAttribute("hilos", hilos);
        return "foro/lista";
    }

    //@GetMapping("/{id}")
    public String verHilo(@PathVariable int id, Model model) {
        Optional<Hiloforo> hiloOpt = hiloRepo.findById(id);
        if (hiloOpt.isPresent()) {
            Hiloforo hilo = hiloOpt.get();
            model.addAttribute("hilo", hilo);

            List<ComentarioForo> comentarios = comentarioRepo.findByHilo(hilo);
            model.addAttribute("comentarios", comentarios);

            return "foro/hilo";
        }
        return "redirect:/foro";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoHilo(Model model) {
        List<Temaforo> temas = temaRepo.findAll();
        model.addAttribute("temas", temas);
        return "foro/nuevoHilo";
    }

    @PostMapping("/nuevo")
    public String crearNuevoHilo(@RequestParam String titulo,
                                 @RequestParam String contenido,
                                 @RequestParam int temaId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttrs) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        if (usuario == null) {
            redirectAttrs.addFlashAttribute("error", "Debes estar logueado para crear un hilo.");
            return "redirect:/login";
        }

        Optional<Temaforo> temaOpt = temaRepo.findById(temaId);
        if (temaOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Tema no válido.");
            return "redirect:/foro/nuevo";
        }

        Hiloforo nuevoHilo = new Hiloforo();
        nuevoHilo.setFechaCreacion(LocalDateTime.now());
        nuevoHilo.setPreguntaID(temaId);
        hiloRepo.save(nuevoHilo);

        ComentarioForo primerComentario = new ComentarioForo();
        primerComentario.setHilo(nuevoHilo);
        primerComentario.setUsuario(usuarioService.findUsuarioByIdUsuario(usuario.getUsuarioID()));
        primerComentario.setContenido(contenido);
        primerComentario.setFechaHora(LocalDateTime.now());
        comentarioRepo.save(primerComentario);

        redirectAttrs.addFlashAttribute("mensaje", "Hilo creado correctamente.");
        return "redirect:/foro/" + nuevoHilo.getHiloID();
    }

}

