package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;
import edu.mondragon.webengl.domain.foro.model.PreguntaFrecuente;
import edu.mondragon.webengl.domain.foro.model.Temaforo;
import edu.mondragon.webengl.domain.foro.repository.ComentarioForoRepository;
import edu.mondragon.webengl.domain.foro.repository.HiloforoRepository;
import edu.mondragon.webengl.domain.foro.repository.PreguntaFrecuenteRepository;
import edu.mondragon.webengl.domain.foro.repository.TemaforoRepository;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepo;
    private static final Logger logger = LoggerFactory.getLogger(ForoController.class);


    public ForoController(HiloforoRepository hiloRepo, ComentarioForoRepository comentarioRepo, TemaforoRepository temaRepo, UsuarioRepository usuarioRepo, PreguntaFrecuenteRepository preguntaFrecuenteRepo) {
        this.hiloRepo = hiloRepo;
        this.comentarioRepo = comentarioRepo;
        this.temaRepo = temaRepo;
        this.usuarioRepo = usuarioRepo;
        this.preguntaFrecuenteRepo = preguntaFrecuenteRepo;
    }

    @GetMapping("/principal")
    public String principal(Model model, HttpSession session) {
        List<Temaforo> temas = temaRepo.findAll();
        model.addAttribute("paginaActual", "foro");
        model.addAttribute("temaForo", temas);
        return "foros/forosPrincipal";
    }

   // @GetMapping("/{temaID}")
    @GetMapping("/{temaID:[0-9]+}")
    public String foroPorTema(@PathVariable int temaID, Model model, HttpSession session) {
        Optional<Temaforo> temaOpt = temaRepo.findById(temaID);
        logger.info("\n\nAccediendo al foro del tema: {}", temaID);
        model.addAttribute("paginaActual", "foro");

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
    public String accederPreguntaUsuario(@PathVariable int preguntaID, @AuthenticationPrincipal UsuarioDetails usuario, Model model) {
        Optional<PreguntaFrecuente> preguntaOpt = preguntaFrecuenteRepo.findById(preguntaID);
        logger.info("\n\nAccediendo a la pregunta frecuente: {}\n\n", preguntaID);
        if (preguntaOpt.isPresent()) {
            Optional<Hiloforo> hiloOpt = hiloRepo.findByPreguntaID(preguntaID);
            if (hiloOpt.isPresent()) {
                Hiloforo hilo = hiloOpt.get();
                List<ComentarioForo> comentarios = comentarioRepo.findByHiloOrderByBotoPosDesc(hiloOpt.get());
                model.addAttribute("hilo", hilo);
                model.addAttribute("comentarios", comentarios);
                model.addAttribute("pregunta", preguntaOpt.get());
                model.addAttribute("usuarioLogueado", usuario.getUsuario());
                model.addAttribute("paginaActual", "foro");
                // Añade el temaID al modelo
                model.addAttribute("temaID", preguntaOpt.get().getTema().getTemaID());
            }
            return "foros/pregunta";
        }
        return "redirect:/foro";
    }

    @PostMapping("/preguntasFrecuentes")
    public String crearPreguntaFrecuente(@RequestParam String contenido, @RequestParam int temaId,
                                        RedirectAttributes redirectAttrs, Model model) {

        Temaforo tema = temaRepo.findById(temaId).orElse(null);
        if (tema == null) {
            redirectAttrs.addFlashAttribute("error", "Tema no válido.");
            return "redirect:/foro";
        }

        PreguntaFrecuente nuevaPregunta = new PreguntaFrecuente();
        nuevaPregunta.setPregunta(contenido);
        nuevaPregunta.setTema(tema);
        nuevaPregunta.setFechaCreacion(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        preguntaFrecuenteRepo.saveAndFlush(nuevaPregunta);

         // Crear el hilo asociado a la nueva pregunta
        Hiloforo nuevoHilo = new Hiloforo();
        nuevoHilo.setPreguntaID(nuevaPregunta.getPreguntaID());
        nuevoHilo.setFechaCreacion(LocalDateTime.now());
        hiloRepo.save(nuevoHilo);

        // Redirige al foro del tema correspondiente
        return "redirect:/foro/" + tema.getTemaID();
    }

    @PostMapping("/{hiloID}/{preguntaID}/comentario")
    public String agregarComentario(@PathVariable int hiloID,
                                    @PathVariable int preguntaID,
                                    @RequestParam String contenido,
                                    HttpSession session,
                                    @AuthenticationPrincipal UsuarioDetails usuario,
                                    RedirectAttributes redirectAttrs,
                                    Model model) {


        Optional<Hiloforo> hiloOpt = hiloRepo.findById(hiloID);
        if (hiloOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "El hilo no existe.");
            return "redirect:/foro";
        }

        ComentarioForo comentario = new ComentarioForo();
        comentario.setHilo(hiloOpt.get());
        comentario.setUsuario(usuarioRepo.findById(usuario.getUsuario().getUsuarioID()).orElse(null));
        comentario.setContenido(contenido);
        comentario.setFechaHora(LocalDateTime.now());
        comentario.setBotoPos(0); // Inicializar en 0
        comentarioRepo.save(comentario);

        List<ComentarioForo> comentarios = comentarioRepo.findByHiloOrderByBotoPosDesc(hiloOpt.get());
        model.addAttribute("comentarios", comentarios);

        model.addAttribute("pregunta", preguntaFrecuenteRepo.findById(preguntaID).orElse(null));
        model.addAttribute("hilo", hiloOpt.get());
        model.addAttribute("usuarioLogueado", usuario.getUsuario()); 

        return "redirect:/foro/preguntaUsuario/" + preguntaID;
    }

    @PostMapping("/{hiloID}/{preguntaID}/{comentarioID}")
    public String likeComentario(@PathVariable int hiloID, @PathVariable int preguntaID, @PathVariable int comentarioID) {
        Optional<Hiloforo> hiloOpt = hiloRepo.findById(hiloID);
        if (hiloOpt.isEmpty()) {
            return "redirect:/foro";
        }

        Optional<ComentarioForo> comentarioOpt = comentarioRepo.findById(comentarioID);

        if (comentarioOpt.isPresent()) {
            ComentarioForo comentario = comentarioOpt.get();
            comentario.setBotoPos(comentario.getBotoPos() + 1);
            comentarioRepo.save(comentario);
        }

        return "redirect:/foro/preguntaUsuario/" + preguntaID;
    }

}

