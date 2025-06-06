package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.encuesta.model.Encuesta;
import edu.mondragon.webengl.domain.encuesta.model.EncuestaRespuestas;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuesta;
import edu.mondragon.webengl.domain.encuesta.repository.EncuestaRepository;
import edu.mondragon.webengl.domain.encuesta.repository.HacerEncuestaRepository;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.seguridad.UsuarioDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
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


    public EncuestaController(EncuestaRepository encuestaRepo, HacerEncuestaRepository hacerEncuestaRepo)
    {
        this.encuestaRepo = encuestaRepo;
        this.hacerEncuestaRepo = hacerEncuestaRepo;
    }

    // Lista todas las encuestas disponibles
    @GetMapping()
    public String listarEncuestas(Model model)
    {
        List<Encuesta> encuestas = encuestaRepo.findAll();
        model.addAttribute("encuestas", encuestas);
        return "encuestas/lista";
    }

    // Mostrar formulario para responder encuesta
    @GetMapping("/{encuestaID}")
    public String mostrarEncuesta(@PathVariable("encuestaID") int encuestaID,
                                Model model,
                                HttpSession session,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttrs,
                                @AuthenticationPrincipal UsuarioDetails user) {
        // Buscar la encuesta específica
        Optional<Encuesta> encuestaOpt = encuestaRepo.findById(encuestaID);
        if (encuestaOpt.isEmpty())
        {
            redirectAttrs.addFlashAttribute("error", "Encuesta no encontrada");
            return "redirect:/encuestas";
        }

        // Verificar si el usuario está autenticado
        Usuario usuario = user.getUsuario();
        if (usuario == null)
        {
            // Guardar la URL original
            String originalUrl = request.getRequestURL().toString();
            session.setAttribute("urlAnterior", originalUrl);
            redirectAttrs.addFlashAttribute("error", "Debes iniciar sesión para responder la encuesta");
            return "redirect:/login";
        }

        // Aquí podrías añadir lógica para evitar que un usuario conteste dos veces la misma encuesta si quieres

        // Añadir la encuesta y el objeto para respuestas al modelo
        // Añadir la encuesta al modelo
        model.addAttribute("encuesta", encuestaOpt.get());
        // Añadir el DTO vacío al modelo para el binding del formulario
        model.addAttribute("encuestaRespuestas", new EncuestaRespuestas());

        return "encuestas/encuesta";
    }

    // Procesar respuestas de la encuesta
    @PostMapping("/{encuestaID}/responder")
    public String guardarRespuesta(@PathVariable("encuestaID") int encuestaID,
                                   @ModelAttribute EncuestaRespuestas encuestaRespuestas, //@RequestParam String respuestas, // ajusta según tus datos, por ejemplo JSON o params separados
                                   HttpSession session,
                                   RedirectAttributes redirectAttrs,
                                   @AuthenticationPrincipal UsuarioDetails user) {

        Optional<Encuesta> encuestaOpt = encuestaRepo.findById(encuestaID);
        if (encuestaOpt.isEmpty())
        {
            redirectAttrs.addFlashAttribute("error", "Encuesta no encontrada");
            return "redirect:/encuestas";
        }

        // Aquí guardamos la respuesta (simplificado)
        // HacerEncuesta he = new HacerEncuesta();
        // he.setEncuesta(encuestaOpt.get());
        // he.setFecha(LocalDateTime.now());
        // he.setRespuestas(respuestas); // campo ejemplo, ajusta según tu modelo

        // Crear nueva respuesta de encuesta
        HacerEncuesta he = new HacerEncuesta();
        he.setEncuestaID(encuestaID);
        he.setUsuarioID(user.getUsuario().getUsuarioID());

        // Establecer título y descripción desde la encuesta original
        Encuesta encuesta = encuestaOpt.get();
        he.setTitulo(encuesta.getTitulo());
        he.setDescripcion(encuesta.getDescripcion());

        try
        {
            // Calcular y setear las puntuaciones 
            double resultadoPsi = encuestaRespuestas.getPsicologicoConexion() + encuestaRespuestas.getPsicologicoExtranjero()
                        + encuestaRespuestas.getPsicologicoDeseoVivir() + encuestaRespuestas.getPsicologicoAislamiento();
            // he.setResultadoPsicologico(resultadoPsi);

            double resultadoLin = encuestaRespuestas.getLinguisticoLectura() + encuestaRespuestas.getLinguisticoConversacion()
                        + encuestaRespuestas.getLinguisticoEscritura() + encuestaRespuestas.getLinguisticoEscucha();
            // he.setResultadoLinguistico(resultadoLin);

            double resultadoEco = encuestaRespuestas.getEconomicoIngreso() + encuestaRespuestas.getEconomicoSituacion()
                        + encuestaRespuestas.getEconomicoGasto400() + encuestaRespuestas.getEconomicoGasto800()
                        + encuestaRespuestas.getEconomicoGasto8000() + encuestaRespuestas.getEconomicoGasto40000() + 1
                        + encuestaRespuestas.getEconomicoSatisfaccion();
            // he.setResultadoEconomico(resultadoEco);
            
            int resultadoPol4 = encuestaRespuestas.getPoliticoAccionConvencer() + encuestaRespuestas.getPoliticoAccionInfluirVoto()
                        + encuestaRespuestas.getPoliticoAccionDeclaracion() + encuestaRespuestas.getPoliticoAccionDiscusionPublica()
                        + encuestaRespuestas.getPoliticoAccionContacto() + encuestaRespuestas.getPoliticoAccionTrabajoPartido()
                        + encuestaRespuestas.getPoliticoAccionInsignia() + encuestaRespuestas.getPoliticoAccionFirmaPeticion()
                        + encuestaRespuestas.getPoliticoAccionManifestacion() + encuestaRespuestas.getPoliticoAccionBoicot()
                        + encuestaRespuestas.getPoliticoAccionRecogerFirmas();
            
            double resultadoPol = encuestaRespuestas.getPoliticoComprension() + encuestaRespuestas.getPoliticoDiscusion()
                        + encuestaRespuestas.getPoliticoPartidos() + encuestaRespuestas.getPoliticoPresidentePartido()
                        + encuestaRespuestas.getPoliticoSenadoPartido() + Integer.valueOf(encuestaRespuestas.getPoliticoEdadVoto()) + 1
                        + (resultadoPol4 > 4 ? 5 : (resultadoPol4 == 3 || resultadoPol4 == 4) ? 4 : (resultadoPol4 == 2) ? 3 : (resultadoPol4 == 1) ? 2 : 1);
            // he.setResultadoPolitico(resultadoPol);

            int resultadoSoc3A = (encuestaRespuestas.getSocialParticipacionGrupoA() == 1 ? 0 : encuestaRespuestas.getSocialParticipacionGrupoA())
                            * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoA() == 1 ? 0 : encuestaRespuestas.getSocialMiembrosEspanolesGrupoA());
            int resultadoSoc3B = (encuestaRespuestas.getSocialParticipacionGrupoB() == 1 ? 0 : encuestaRespuestas.getSocialParticipacionGrupoB())
                            * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoB() == 1 ? 0 : encuestaRespuestas.getSocialMiembrosEspanolesGrupoB());
            int resultadoSoc3C = (encuestaRespuestas.getSocialParticipacionGrupoC() == 1 ? 0 : encuestaRespuestas.getSocialParticipacionGrupoC())
                            * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoC() == 1 ? 0 : encuestaRespuestas.getSocialMiembrosEspanolesGrupoC());
            int resultadoSoc3D = (encuestaRespuestas.getSocialParticipacionGrupoD() == 1 ? 0 : encuestaRespuestas.getSocialParticipacionGrupoD())
                            * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoD() == 1 ? 0 : encuestaRespuestas.getSocialMiembrosEspanolesGrupoD());
            int resultadoSoc3E = (encuestaRespuestas.getSocialParticipacionGrupoE() == 1 ? 0 : encuestaRespuestas.getSocialParticipacionGrupoE())
                            * (encuestaRespuestas.getSocialMiembrosEspanolesGrupoE() == 1 ? 0 : encuestaRespuestas.getSocialMiembrosEspanolesGrupoE());
            int resultadoSoc3 = Math.max(Math.max(resultadoSoc3A, resultadoSoc3B),Math.max(Math.max(resultadoSoc3C, resultadoSoc3D), resultadoSoc3E));

            double resultadoSoc = encuestaRespuestas.getSocialCenaEspanoles() + encuestaRespuestas.getSocialContactosEspanoles()
                        + ((int)Math.ceil((resultadoSoc3 / 25.0) * 4)) + encuestaRespuestas.getSocialFavoresEspanoles();
            // he.setResultadoSocial(resultadoSoc);

            double resultadoNav = encuestaRespuestas.getNavegacionalConsultaMedica() + encuestaRespuestas.getNavegacionalBuscarEmpleo()
                        + encuestaRespuestas.getNavegacionalAyudaLegal() + encuestaRespuestas.getNavegacionalConduccionAlcohol()
                        + encuestaRespuestas.getNavegacionalPagoImpuestos() + encuestaRespuestas.getNavegacionalFormatoDireccion()
                        + encuestaRespuestas.getNavegacionalAyudaMedicaCronica() + 1;
            // he.setResultadoNavegacional(resultadoNav);
            
            double indiceTotal = (((double)resultadoPsi + (double)resultadoLin + (double)resultadoEco + (double)resultadoPol + (double)resultadoSoc + (double)resultadoNav) - 24) / (120 - 24) * (1 - 0) + 0;
            he.setResultadoTotal(indiceTotal);

            double indicePsi = (resultadoPsi - 4) / (20 - 4) * (1 - 0) + 0;
            double indiceLin = (resultadoLin - 4) / (20 - 4) * (1 - 0) + 0;
            double indiceEco = (resultadoEco - 4) / (20 - 4) * (1 - 0) + 0;
            double indicePol = (resultadoPol - 4) / (20 - 4) * (1 - 0) + 0;
            double indiceSoc = (resultadoSoc - 4) / (20 - 4) * (1 - 0) + 0;
            double indiceNav = (resultadoNav - 4) / (20 - 4) * (1 - 0) + 0;

            he.setResultadoPsicologico(indicePsi);
            he.setResultadoLinguistico(indiceLin);
            he.setResultadoEconomico(indiceEco);
            he.setResultadoPolitico(indicePol);
            he.setResultadoSocial(indiceSoc);
            he.setResultadoNavegacional(indiceNav);

            // Debug para verificar valores
            System.out.println("Indices:");
            System.out.println("Psicológico: " + indicePsi);
            System.out.println("Lingüístico: " + indiceLin);
            System.out.println("Económico: " + indiceEco);
            System.out.println("Político: " + indicePol);
            System.out.println("Social: " + indiceSoc);
            System.out.println("Navegacional: " + indiceNav);
            System.out.println("Total: " + indiceTotal);

            hacerEncuestaRepo.save(he);

            redirectAttrs.addFlashAttribute("mensaje", "¡Gracias por responder la encuesta!");
            return "redirect:/encuestas/graficoEncuesta";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Error al procesar la encuesta: " + e.getMessage());
            return "redirect:/encuestas";// /" + encuestaID;
        }
    }

    @GetMapping("/graficoEncuesta")
    public String mostrarGrafico(Model model, @AuthenticationPrincipal UsuarioDetails user)
    {
        // Obtener los resultados de la última encuesta del usuario
        Optional<HacerEncuesta> ultimaEncuesta = hacerEncuestaRepo
            .findFirstByUsuarioIDOrderByEncuestaIDDesc(user.getUsuario().getUsuarioID());
        
        if (ultimaEncuesta.isPresent())
        {
            model.addAttribute("encuesta", ultimaEncuesta.get());
            // Debug para verificar valores
            HacerEncuesta he = ultimaEncuesta.get();
            System.out.println("Valores en gráfico:");
            System.out.println("Psicológico: " + he.getResultadoPsicologico());
            System.out.println("Lingüístico: " + he.getResultadoLinguistico());
            System.out.println("Económico: " + he.getResultadoEconomico());
            System.out.println("Político: " + he.getResultadoPolitico());
            System.out.println("Social: " + he.getResultadoSocial());
            System.out.println("Navegacional: " + he.getResultadoNavegacional());
        }
        
        return "encuestas/graficoEncuesta";
    }
}