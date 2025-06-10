package edu.mondragon.webengl.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.categoria.repository.CategoriaRepository;
import edu.mondragon.webengl.domain.evento.model.EventoLocal;
import edu.mondragon.webengl.domain.evento.repository.EventoLocalRepository;
import java.util.List;
//import edu.mondragon.webengl.helper.ControllerHelper;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final EventoLocalRepository eventoRepo;
    private final CategoriaRepository categoriaRepository;


    public LoginController(EventoLocalRepository eventoRepo, CategoriaRepository categoriaRepository) {
        this.eventoRepo = eventoRepo;
        this.categoriaRepository = categoriaRepository;
    }
    
    @GetMapping
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        return "login/login"; // muestra el login.html
    }

   @GetMapping("/logged")
    public String logged(Model model) {
        List<EventoLocal> eventos = eventoRepo.findAll();
        model.addAttribute("eventos", eventos);
        model.addAttribute("categorias", categoriaRepository.findAll()); 
        model.addAttribute("tituloPagina", "Eventos locales");
        model.addAttribute("textoNoEventos", "No hay eventos disponibles para esta categoría.");

        return "evento/eventosListaGenerica";
    }
    @GetMapping("/login/logged")
    public String redirigirEventos() {
        return "redirect:/eventos";
    }
}


