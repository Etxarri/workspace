package edu.mondragon.webengl.controller;

import edu.mondragon.webengl.domain.recurso.model.RecursoLocal;
import edu.mondragon.webengl.domain.recurso.repository.RecursoLocalRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CiudadInfoController {

    private final RecursoLocalRepository recursoLocalRepository;
    private final UsuarioRepository usuarioRepository;

    public CiudadInfoController(RecursoLocalRepository recursoLocalRepository,
                                EventoLocalRepository eventoLocalRepository,
                                UsuarioRepository usuarioRepository) {
        this.recursoLocalRepository = recursoLocalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/ciudadInfo")
    public String mostrarInformacionCiudad(Model model, Principal principal, @RequestParam(value = "categoria", required = false) String categoria) {
        String username = principal.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        if (usuario == null) {
            return "redirect:/login/logged";
        }
        System.out.println("not null");
        int ciudadId = usuario.getCiudad().getCiudadID();

        List<RecursoLocal> recursos = recursoLocalRepository.findByCiudad_CiudadID(ciudadId);

        model.addAttribute("usuario", usuario);
        model.addAttribute("recursos", recursos);
        return "ciudadInfo";
    }
}

