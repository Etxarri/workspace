package edu.mondragon.webengl.controller;

import edu.mondragon.webengl.domain.pais.repository.CiudadRepository;
import edu.mondragon.webengl.domain.pais.repository.PaisRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.model.Usuario.TipoUsuario;
import edu.mondragon.webengl.domain.user.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final CiudadRepository ciudadRepository;
    private final PaisRepository paisRepository;   

    public UsuarioController(UsuarioService usuarioService, CiudadRepository ciudadRepository, PaisRepository paisRepository) {
        this.usuarioService = usuarioService;
        this.ciudadRepository = ciudadRepository;
        this.paisRepository = paisRepository;
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
            @RequestParam int paisID,
            @RequestParam int ciudadID,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (usuarioService.existeUsuarioPorEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "El email ya está registrado.");
            return "redirect:/usuario/crear";
        }

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

        Usuario usuarioCreado = usuarioService.findUsuarioByEmail(email).orElse(null);
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
}