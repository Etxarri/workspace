package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.encuesta.model.Encuesta;
import edu.mondragon.webengl.domain.encuesta.model.EncuestaRespuestas;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuesta;
import edu.mondragon.webengl.domain.encuesta.repository.EncuestaRepository;
import edu.mondragon.webengl.domain.encuesta.repository.HacerEncuestaRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/encuestas")
public class EncuestaController
{
    /* NOTAS del CHARLY:
     * La variable 'respuestas' es un ejemplo, normalmente tendrás que adaptar la forma de capturar y almacenar las respuestas (quizás usando un DTO o un formulario complejo).
     * Asegúrate de tener en tu entidad HacerEncuesta el campo para guardar esas respuestas.
     * Puedes implementar validaciones para evitar respuestas repetidas por usuario.
     */

    private final EncuestaRepository encuestaRepo;
    private final HacerEncuestaRepository hacerEncuestaRepo;

    //private final EncuestaRespuestas encuestaRespuestas;

    public EncuestaController(EncuestaRepository encuestaRepo, HacerEncuestaRepository hacerEncuestaRepo)
    {
        this.encuestaRepo = encuestaRepo;
        this.hacerEncuestaRepo = hacerEncuestaRepo;

        //this.encuestaRespuestas = encuestaRespuestas;
    }

    // Lista todas las encuestas disponibles
    @GetMapping
    public String listarEncuestas(Model model)
    {
        List<Encuesta> encuestas = encuestaRepo.findAll();
        model.addAttribute("encuestas", encuestas);
        return "encuestas/lista";
    }

    // Mostrar formulario para responder encuesta
    @GetMapping("/{id}")
    public String mostrarEncuesta(@PathVariable int id, Model model, HttpSession session, RedirectAttributes redirectAttrs)
    {
        Optional<Encuesta> encuestaOpt = encuestaRepo.findById(id);
        if (encuestaOpt.isEmpty())
        {
            redirectAttrs.addFlashAttribute("error", "Encuesta no encontrada");
            return "redirect:/encuestas";
        }

        Usuario usuario = (Usuario) session.getAttribute("user");
        if (usuario == null)
        {
            redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión para responder la encuesta");
            return "redirect:/login";
        }

        // Aquí podrías añadir lógica para evitar que un usuario conteste dos veces la misma encuesta si quieres

        model.addAttribute("encuesta", encuestaOpt.get());

        // Añadir el DTO vacío al modelo para el binding del formulario
        model.addAttribute("encuestaRespuestas", new EncuestaRespuestas());

        return "encuestas/responder";
    }

    // Procesar respuestas de la encuesta
    @PostMapping("/{id}/responder")
    public String guardarRespuesta(@PathVariable int id,
                                   @ModelAttribute EncuestaRespuestas encuestaRespuestas, //@RequestParam String respuestas, // ajusta según tus datos, por ejemplo JSON o params separados
                                   HttpSession session,
                                   RedirectAttributes redirectAttrs) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        if (usuario == null)
        {
            redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión para responder la encuesta");
            return "redirect:/login";
        }

        Optional<Encuesta> encuestaOpt = encuestaRepo.findById(id);
        if (encuestaOpt.isEmpty())
        {
            redirectAttrs.addFlashAttribute("error", "Encuesta no encontrada");
            return "redirect:/encuestas";
        }

        // Aquí guardamos la respuesta (simplificado)
        HacerEncuesta he = new HacerEncuesta();
        he.setEncuesta(encuestaOpt.get());
        he.setUsuario(usuario);
        //he.setFecha(LocalDateTime.now());
        //he.setRespuestas(respuestas); // campo ejemplo, ajusta según tu modelo


        // Calcular y setear las puntuaciones (manejar NullPointerExceptions si alguna respuesta no es obligatoria)
        // Es buena práctica verificar si los getters del DTO devuelven null antes de sumarlos
        // si no todas las preguntas son obligatorias. Para simplificar, asumimos que se responden.

        int puntosPsi = (encuestaRespuestas.getPsicologicoConexion() != null ? encuestaRespuestas.getPsicologicoConexion() : 0)
                     + (encuestaRespuestas.getPsicologicoExtranjero() != null ? encuestaRespuestas.getPsicologicoExtranjero() : 0)
                     + (encuestaRespuestas.getPsicologicoDeseoVivir() != null ? encuestaRespuestas.getPsicologicoDeseoVivir() : 0)
                     + (encuestaRespuestas.getPsicologicoAislamiento() != null ? encuestaRespuestas.getPsicologicoAislamiento() : 0);
        he.setPuntosPsicologico(puntosPsi);

        int puntosLin = (encuestaRespuestas.getLinguisticoLectura() != null ? encuestaRespuestas.getLinguisticoLectura() : 0)
                     + (encuestaRespuestas.getLinguisticoConversacion() != null ? encuestaRespuestas.getLinguisticoConversacion() : 0)
                     + (encuestaRespuestas.getLinguisticoEscritura() != null ? encuestaRespuestas.getLinguisticoEscritura() : 0)
                     + (encuestaRespuestas.getLinguisticoEscucha() != null ? encuestaRespuestas.getLinguisticoEscucha() : 0);
        he.setPuntosLinguistico(puntosLin);

        int puntosEco = (encuestaRespuestas.getEconomicoIngreso() != null ? encuestaRespuestas.getEconomicoIngreso() : 0)
                     + (encuestaRespuestas.getEconomicoSituacion() != null ? encuestaRespuestas.getEconomicoSituacion() : 0)
                     + (encuestaRespuestas.getEconomicoGasto400() != null ? encuestaRespuestas.getEconomicoGasto400() : 0)
                     + (encuestaRespuestas.getEconomicoGasto800() != null ? encuestaRespuestas.getEconomicoGasto800() : 0)
                     + (encuestaRespuestas.getEconomicoGasto8000() != null ? encuestaRespuestas.getEconomicoGasto8000() : 0)
                     + (encuestaRespuestas.getEconomicoGasto40000() != null ? encuestaRespuestas.getEconomicoGasto40000() : 0)
                     + 1
                     + (encuestaRespuestas.getEconomicoSatisfaccion() != null ? encuestaRespuestas.getEconomicoSatisfaccion() : 0);
        he.setPuntosEconomico(puntosEco);
        
        int puntosPol4 = (encuestaRespuestas.getPoliticoAccionConvencer() != null ? encuestaRespuestas.getPoliticoAccionConvencer() : 0)
                     + (encuestaRespuestas.getPoliticoAccionInfluirVoto() != null ? encuestaRespuestas.getPoliticoAccionInfluirVoto() : 0)
                     + (encuestaRespuestas.getPoliticoAccionDeclaracion() != null ? encuestaRespuestas.getPoliticoAccionDeclaracion() : 0)
                     + (encuestaRespuestas.getPoliticoAccionDiscusionPublica() != null ? encuestaRespuestas.getPoliticoAccionDiscusionPublica() : 0)
                     + (encuestaRespuestas.getPoliticoAccionContacto() != null ? encuestaRespuestas.getPoliticoAccionContacto() : 0)
                     + (encuestaRespuestas.getPoliticoAccionTrabajoPartido() != null ? encuestaRespuestas.getPoliticoAccionTrabajoPartido() : 0)
                     + (encuestaRespuestas.getPoliticoAccionInsignia() != null ? encuestaRespuestas.getPoliticoAccionInsignia() : 0)
                     + (encuestaRespuestas.getPoliticoAccionFirmaPeticion() != null ? encuestaRespuestas.getPoliticoAccionFirmaPeticion() : 0)
                     + (encuestaRespuestas.getPoliticoAccionManifestacion() != null ? encuestaRespuestas.getPoliticoAccionManifestacion() : 0)
                     + (encuestaRespuestas.getPoliticoAccionBoicot() != null ? encuestaRespuestas.getPoliticoAccionBoicot() : 0)
                     + (encuestaRespuestas.getPoliticoAccionRecogerFirmas() != null ? encuestaRespuestas.getPoliticoAccionRecogerFirmas() : 0);
        
        int puntosPol = (encuestaRespuestas.getPoliticoComprension() != null ? encuestaRespuestas.getPoliticoComprension() : 0)
                     + (encuestaRespuestas.getPoliticoDiscusion() != null ? encuestaRespuestas.getPoliticoDiscusion() : 0)
                     + (encuestaRespuestas.getPoliticoPartidos() != null ? encuestaRespuestas.getPoliticoPartidos() : 0)
                     + (encuestaRespuestas.getPoliticoPresidentePartido() != null ? encuestaRespuestas.getPoliticoPresidentePartido() : 0)
                     + (encuestaRespuestas.getPoliticoSenadoPartido() != null ? encuestaRespuestas.getPoliticoSenadoPartido() : 0)
                     + (encuestaRespuestas.getPoliticoEdadVoto() != null ? Integer.parseInt(encuestaRespuestas.getPoliticoEdadVoto()) : 0)
                     + 1
                     + (puntosPol4 > 4 ? 5 : (puntosPol4 == 3 || puntosPol4 == 4) ? 4 : (puntosPol4 == 2) ? 3 : (puntosPol4 == 1) ? 2 : 1);
        he.setPuntosPolitico(puntosPol);

        int puntosSoc3A = (encuestaRespuestas.getSocialParticipacionGrupoA() != null ? encuestaRespuestas.getSocialParticipacionGrupoA() : 1)
                        * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoA() != null ? (encuestaRespuestas.getSocialParticipacionGrupoA() != 1 ? encuestaRespuestas.getSocialMiembrosEspanolesGrupoA() : 0) : 0);
        int puntosSoc3B = (encuestaRespuestas.getSocialParticipacionGrupoB() != null ? encuestaRespuestas.getSocialParticipacionGrupoB() : 1)
                        * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoB() != null ? (encuestaRespuestas.getSocialParticipacionGrupoB() != 1 ? encuestaRespuestas.getSocialMiembrosEspanolesGrupoB() : 0) : 0);
        int puntosSoc3C = (encuestaRespuestas.getSocialParticipacionGrupoC() != null ? encuestaRespuestas.getSocialParticipacionGrupoC() : 1)
                        * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoC() != null ? (encuestaRespuestas.getSocialParticipacionGrupoC() != 1 ? encuestaRespuestas.getSocialMiembrosEspanolesGrupoC() : 0) : 0);
        int puntosSoc3D = (encuestaRespuestas.getSocialParticipacionGrupoD() != null ? encuestaRespuestas.getSocialParticipacionGrupoD() : 1)
                        * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoD() != null ? (encuestaRespuestas.getSocialParticipacionGrupoD() != 1 ? encuestaRespuestas.getSocialMiembrosEspanolesGrupoD() : 0) : 0);
        int puntosSoc3E = (encuestaRespuestas.getSocialParticipacionGrupoE() != null ? encuestaRespuestas.getSocialParticipacionGrupoE() : 1)
                        * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoE() != null ? (encuestaRespuestas.getSocialParticipacionGrupoE() != 1 ? encuestaRespuestas.getSocialMiembrosEspanolesGrupoE() : 0) : 0);

        int puntosSoc3 = Math.max(Math.max(puntosSoc3A, puntosSoc3B),Math.max(Math.max(puntosSoc3C, puntosSoc3D), puntosSoc3E));

        int puntosSoc = (encuestaRespuestas.getSocialCenaEspanoles() != null ? encuestaRespuestas.getSocialCenaEspanoles() : 0)
                     + (encuestaRespuestas.getSocialContactosEspanoles() != null ? encuestaRespuestas.getSocialContactosEspanoles() : 0)
                     + ((int)Math.ceil((puntosSoc3 / 25.0) * 4))
                     + (encuestaRespuestas.getSocialFavoresEspanoles() != null ? encuestaRespuestas.getSocialFavoresEspanoles() : 0);
        he.setPuntosSocial(puntosSoc);

        int puntosNav = (encuestaRespuestas.getNavegacionalConsultaMedica() != null ? encuestaRespuestas.getNavegacionalConsultaMedica() : 0)
                     + (encuestaRespuestas.getNavegacionalBuscarEmpleo() != null ? encuestaRespuestas.getNavegacionalBuscarEmpleo() : 0)
                     + (encuestaRespuestas.getNavegacionalAyudaLegal() != null ? encuestaRespuestas.getNavegacionalAyudaLegal() : 0)
                     + (encuestaRespuestas.getNavegacionalConduccionAlcohol() != null ? encuestaRespuestas.getNavegacionalConduccionAlcohol() : 0)
                     + (encuestaRespuestas.getNavegacionalPagoImpuestos() != null ? encuestaRespuestas.getNavegacionalPagoImpuestos() : 0)
                     + (encuestaRespuestas.getNavegacionalFormatoDireccion() != null ? encuestaRespuestas.getNavegacionalFormatoDireccion() : 0)
                     + (encuestaRespuestas.getNavegacionalAyudaMedicaCronica() != null ? encuestaRespuestas.getNavegacionalAyudaMedicaCronica() : 0)
                     + 1;
        he.setPuntosNavegacional(puntosNav);
        
        double indiceTotal = ((puntosPsi + puntosLin + puntosEco + puntosPol + puntosSoc + puntosNav) - 24) / (120 - 24) * (1 - 0) + 0;
        he.setPuntosTotal(indiceTotal);


        hacerEncuestaRepo.save(he);

        redirectAttrs.addFlashAttribute("mensaje", "Gracias por responder la encuesta");
        return "redirect:/encuestas";
    }
}

