package edu.mondragon.webengl.controller;

import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.pais.model.Pais;
import edu.mondragon.webengl.domain.pais.repository.CiudadRepository;
import edu.mondragon.webengl.domain.pais.repository.PaisRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.model.Usuario.TipoUsuario;
import edu.mondragon.webengl.domain.user.repository.RecienllegadoRepository;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import edu.mondragon.webengl.domain.user.repository.VoluntarioRepository;
import edu.mondragon.webengl.domain.user.service.UsuarioService;
import edu.mondragon.webengl.seguridad.UsuarioDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final CiudadRepository ciudadRepository;
    private final PaisRepository paisRepository;  
    private final UsuarioRepository usuarioRepo; 
    private final UsuarioService usuarioService;
    private final VoluntarioRepository voluntarioRepo;
    private final RecienllegadoRepository recienllegadoRepo;

    private static final Logger logger = LoggerFactory.getLogger(EventoLocalController.class);


    public UsuarioController(UsuarioRepository usuarioRepo, CiudadRepository ciudadRepository, PaisRepository paisRepository, UsuarioService usuarioService, VoluntarioRepository voluntarioRepo, RecienllegadoRepository recienllegadoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.ciudadRepository = ciudadRepository;
        this.paisRepository = paisRepository;
        this.usuarioService = usuarioService;
        this.voluntarioRepo = voluntarioRepo;
        this.recienllegadoRepo = recienllegadoRepo;
    }

    @GetMapping("/crear")
    public String showForm(Model model) {
        model.addAttribute("ciudades", ciudadRepository.findAll());
        model.addAttribute("paises", paisRepository.findAll());
        return "user/userForm"; // Nombre del archivo Thymeleaf para el formulario
    }

    @PostMapping("/crear")
    public String crearUsuario(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String contraseña,
            @RequestParam TipoUsuario tipo,
            @RequestParam(required = false) String necesidades,
            @RequestParam(required = false) String lenguaje,
            @RequestParam(required = false) String habilidades,
            @RequestParam(required = false) String motivacion,
            @RequestParam(required = false) String fecha_llegada,
            @RequestParam int paisID,
            @RequestParam int ciudadID,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (usuarioRepo.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado.");
                    logger.info("\n \naasdfasdfa\n");

            return "redirect:/usuario/crear";
        }
        logger.info("\n \naasdfasdfa\n");

        usuarioService.crearUsuario(
            nombre,
            apellido,
            username,
            email,
            usuarioService.encriptarContraseña(contraseña),
            tipo.name(),
            paisID,
            ciudadID
        );

        logger.info("\n \naasdfasdfa\n");

        Usuario usuarioCreado = usuarioRepo.findByEmail(email).orElse(null);
        if (usuarioCreado != null) {
            if (tipo == TipoUsuario.recienllegado) {
                usuarioService.crearRecienllegado(
                    usuarioCreado.getUsuarioID(), necesidades, lenguaje, java.time.LocalDate.now());
            } else if (tipo == TipoUsuario.voluntario) {
                usuarioService.crearVoluntario(
                    usuarioCreado.getUsuarioID(), habilidades, motivacion);
            }
        }

        redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente.");
        return "redirect:/login";
    }

    @GetMapping("/editar")
    public String mostrarFormularioEdicion(Model model, @AuthenticationPrincipal UsuarioDetails usuario, HttpSession session) {
        if (usuario == null) {
            return "redirect:/login";
        }
        Usuario usuarioEdicion = usuarioRepo.findById(usuario.getUsuario().getUsuarioID()).orElse(null);
        model.addAttribute("usuario", usuarioEdicion);
        model.addAttribute("ciudades", ciudadRepository.findAll());
        model.addAttribute("paises", paisRepository.findAll());

        // Cargar datos específicos según el tipo de usuario
        if (usuarioEdicion.getTipo() == Usuario.TipoUsuario.recienllegado) {
            edu.mondragon.webengl.domain.user.model.Recienllegado recienllegado =
                recienllegadoRepo.findById(usuarioEdicion.getUsuarioID()).orElse(null);
            model.addAttribute("lenguaje", recienllegado != null ? recienllegado.getLenguaje() : "");
            model.addAttribute("fecha_llegada", recienllegado != null ? recienllegado.getFechaLlegada() : "");
            model.addAttribute("necesidades", recienllegado != null ? recienllegado.getNecesidades() : "");
        } else if (usuarioEdicion.getTipo() == Usuario.TipoUsuario.voluntario) {
            edu.mondragon.webengl.domain.user.model.Voluntario voluntario =
                voluntarioRepo.findById(usuarioEdicion.getUsuarioID()).orElse(null);
            model.addAttribute("habilidades", voluntario != null ? voluntario.getHabilidades() : "");
            model.addAttribute("motivacion", voluntario != null ? voluntario.getMotivacion() : "");
        }

        model.addAttribute("paginaActual", "perfil");

        return "user/editarUsuario";
    }

    @PostMapping("/editar")
    public String procesarEdicion(
            @RequestParam int usuarioID,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam("ciudadID") int ciudadID,
            @RequestParam("paisID") int paisID,
            @RequestParam TipoUsuario tipo,
            @RequestParam(required = false) String necesidades,
            @RequestParam(required = false) String lenguaje,
            @RequestParam(required = false) String habilidades,
            @RequestParam(required = false) String motivacion,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        Usuario usuario = usuarioRepo.findById(usuarioID).orElse(null);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/usuario/editar";
        }
        Ciudad ciudad = ciudadRepository.findById(ciudadID)
            .orElseThrow(() -> new IllegalArgumentException("Ciudad no encontrada"));
        Pais pais = paisRepository.findById(paisID)
            .orElseThrow(() -> new IllegalArgumentException("Pais no encontrada"));

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setCiudad(ciudad);
        usuario.setPais(pais);
        usuario.setTipo(tipo);

        usuarioService.actualizarUsuario(usuario, necesidades, lenguaje, habilidades, motivacion);

        // Recarga el usuario actualizado desde la base de datos
        Usuario usuarioActualizado = usuarioRepo.findById(usuarioID).orElse(null);
        session.setAttribute("usuario", usuarioActualizado);
        session.setAttribute("paginaActual", "perfil");
        redirectAttributes.addFlashAttribute("success", "Datos actualizados correctamente.");
        return "redirect:/login";
    }
}