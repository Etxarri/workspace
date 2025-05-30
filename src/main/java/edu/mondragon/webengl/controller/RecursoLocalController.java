package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.categoria.repository.CategoriaRepository;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import edu.mondragon.webengl.domain.recurso.model.RecursoLocal;
import edu.mondragon.webengl.domain.recurso.repository.RecursoLocalRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import edu.mondragon.webengl.seguridad.UsuarioDetails;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/recursos")
public class RecursoLocalController {

    private final RecursoLocalRepository recursoLocalRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public RecursoLocalController(RecursoLocalRepository recursoLocalRepository,
                                EventoLocalRepository eventoLocalRepository,
                                UsuarioRepository usuarioRepository,
                                CategoriaRepository categoriaRepository) {
        this.recursoLocalRepository = recursoLocalRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;

    }

    @GetMapping("/ciudadInfo")
    public String mostrarInformacionCiudad(
        Model model, 
        @AuthenticationPrincipal UsuarioDetails usuario, 
        @RequestParam(value = "categoria", required = false) Integer categoriaId) {

        int comunidadId = usuario.getUsuario().getCiudad().getComunidadAutonoma();

        List<RecursoLocal> recursos;
        if (categoriaId != null && categoriaId != 0) {
            recursos = recursoLocalRepository.findByCiudad_ComunidadAutonoma_ComunidadIDAndCategoria_CategoriaID(comunidadId, categoriaId);
        } else {
            recursos = recursoLocalRepository.findByCiudad_ComunidadAutonoma_ComunidadID(comunidadId);
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("recursos", recursos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaId);
        return "recursolocal/ciudadInfo";
    }

    // Mostrar todos los recursos
    @GetMapping
    public String listarRecursos(Model model) {
        List<RecursoLocal> recursos = recursoLocalRepository.findAll();
        model.addAttribute("recursos", recursos);
        return "recursos/lista"; // nombre del template Thymeleaf
    }

    // Mostrar detalles de un recurso concreto
    @GetMapping("/{id}")
    public String mostrarRecurso(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<RecursoLocal> recurso = recursoLocalRepository.findById(id);
        if (recurso.isPresent()) {
            model.addAttribute("recurso", recurso.get());
            return "recursos/detalle";
        } else {
            redirectAttributes.addFlashAttribute("error", "Recurso no encontrado.");
            return "redirect:/recursos";
        }
    }

    // Mostrar formulario para crear un nuevo recurso (si se permite)
    @GetMapping("/crear")
    public String mostrarFormulario(Model model) {
        model.addAttribute("recurso", new RecursoLocal());
        return "recursos/formulario";
    }

    // Guardar el recurso enviado desde el formulario
    @PostMapping("/crear")
    public String guardarRecurso(@ModelAttribute RecursoLocal recurso, RedirectAttributes redirectAttributes) {
        recursoLocalRepository.save(recurso);
        redirectAttributes.addFlashAttribute("mensaje", "Recurso creado correctamente.");
        return "redirect:/recursos";
    }
}

