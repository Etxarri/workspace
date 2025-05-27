package edu.mondragon.webengl.controller;

import edu.mondragon.webengl.domain.pais.model.Ciudad;
import edu.mondragon.webengl.domain.pais.model.Pais;
import edu.mondragon.webengl.domain.pais.repository.CiudadRepository;
import edu.mondragon.webengl.domain.pais.repository.PaisRepository;
import edu.mondragon.webengl.domain.user.model.Recienllegado;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.model.Voluntario;
import edu.mondragon.webengl.domain.user.model.Usuario.TipoUsuario;
import edu.mondragon.webengl.domain.user.service.UsuarioService;
import org.springframework.stereotype.Controller;
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
    public String showForm() {
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

        Pais pais = paisRepository.findById(paisID)
            .orElseThrow(() -> new IllegalArgumentException("País no encontrado"));
        Ciudad ciudad = ciudadRepository.findById(ciudadID)
            .orElseThrow(() -> new IllegalArgumentException("Ciudad no encontrada"));

            Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setContraseña(usuarioService.encriptarContraseña(contraseña));
        usuario.setTipo(tipo);
        usuario.setPais(pais);
        usuario.setCiudad(ciudad);

        usuarioService.guardarUsuario(usuario); // Guardamos primero el usuario base


        if (tipo == TipoUsuario.recienllegado) {
            Recienllegado r = new Recienllegado();
            r.setUsuario(usuario);
            r.setNecesidades(necesidades);
            r.setLenguaje(lenguaje);
            r.setFechaLlegada(java.time.LocalDate.now());
            usuarioService.guardarRecienllegado(r);
        } else if (tipo == TipoUsuario.voluntario) {
            Voluntario v = new Voluntario();
            v.setUsuario(usuario);
            v.setHabilidades(habilidades);
            v.setMotivacion(motivacion);
            usuarioService.guardarVoluntario(v);
        }

        session.setAttribute("usuario", usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente.");
        return "redirect:/login";
    }
}

