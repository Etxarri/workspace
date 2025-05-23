package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.foro.model.ComentarioForo;
import edu.mondragon.webengl.domain.foro.model.Hiloforo;
import edu.mondragon.webengl.domain.foro.model.Temaforo;
import edu.mondragon.webengl.domain.foro.repository.ComentarioForoRepository;
import edu.mondragon.webengl.domain.foro.repository.HiloforoRepository;
import edu.mondragon.webengl.domain.foro.repository.TemaforoRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/foro")
public class ForoController {

    private final HiloforoRepository hiloRepo;
    private final ComentarioForoRepository comentarioRepo;
    private final TemaforoRepository temaRepo;
    private final UsuarioService usuarioService;

    public ForoController(HiloforoRepository hiloRepo, ComentarioForoRepository comentarioRepo, TemaforoRepository temaRepo, UsuarioService usuarioService) {
        this.hiloRepo = hiloRepo;
        this.comentarioRepo = comentarioRepo;
        this.temaRepo = temaRepo;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarHilos(@RequestParam(required = false) int temaId, Model model) {
        List<Hiloforo> hilos;
        if (temaId != 0) {
            Optional<Temaforo> temaOpt = temaRepo.findById((short)temaId);
            if (temaOpt.isPresent()) {
                hilos = hiloRepo.findByIdTema(temaOpt.get().getId());
                model.addAttribute("temaSeleccionado", temaOpt.get());
            } else {
                hilos = hiloRepo.findAll();
            }
        } else {
            hilos = hiloRepo.findAll();
        }
        List<Temaforo> temas = temaRepo.findAll();
        model.addAttribute("temas", temas);
        model.addAttribute("hilos", hilos);
        return "foro/lista";
    }

    @GetMapping("/{id}")
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

        Optional<Temaforo> temaOpt = temaRepo.findById((short)temaId);
        if (temaOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Tema no válido.");
            return "redirect:/foro/nuevo";
        }

        Hiloforo nuevoHilo = new Hiloforo();
        nuevoHilo.setTitulo(titulo);
        nuevoHilo.setIdUsuario(usuario.getUsuarioID());
        nuevoHilo.setFechaCreacion(LocalDateTime.now());
        nuevoHilo.setIdTema(temaOpt.get().getId());
        hiloRepo.save(nuevoHilo);

        ComentarioForo primerComentario = new ComentarioForo();
        primerComentario.setHilo(nuevoHilo);
        primerComentario.setUsuario(usuarioService.findUsuarioByIdUsuario(usuario.getUsuarioID()));
        primerComentario.setContenido(contenido);
        primerComentario.setFechaHora(LocalDateTime.now());
        comentarioRepo.save(primerComentario);

        redirectAttrs.addFlashAttribute("mensaje", "Hilo creado correctamente.");
        return "redirect:/foro/" + nuevoHilo.getId();
    }

    @PostMapping("/{id}/comentario")
    public String agregarComentario(@PathVariable int id,
                                    @RequestParam String contenido,
                                    HttpSession session,
                                    RedirectAttributes redirectAttrs) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        if (usuario == null) {
            redirectAttrs.addFlashAttribute("error", "Debes estar logueado para comentar.");
            return "redirect:/login";
        }

        Optional<Hiloforo> hiloOpt = hiloRepo.findById(id);
        if (hiloOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "El hilo no existe.");
            return "redirect:/foro";
        }

        ComentarioForo comentario = new ComentarioForo();
        comentario.setHilo(hiloOpt.get());
        comentario.setUsuario(usuarioService.findUsuarioByIdUsuario(usuario.getUsuarioID()));
        comentario.setContenido(contenido);
        comentario.setFechaHora(LocalDateTime.now());
        comentarioRepo.save(comentario);

        return "redirect:/foro/" + id;
    }
}

