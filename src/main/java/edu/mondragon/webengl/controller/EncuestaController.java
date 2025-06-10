package edu.mondragon.webengl.controller;


import edu.mondragon.webengl.domain.encuesta.model.ConsejoIntegracion;
import edu.mondragon.webengl.domain.encuesta.model.Encuesta;
import edu.mondragon.webengl.domain.encuesta.model.EncuestaRespuestas;
import edu.mondragon.webengl.domain.encuesta.model.HacerEncuesta;
import edu.mondragon.webengl.domain.encuesta.repository.EncuestaRepository;
import edu.mondragon.webengl.domain.encuesta.repository.HacerEncuestaRepository;
import edu.mondragon.webengl.domain.encuesta.service.ConsejoService;
import edu.mondragon.webengl.domain.user.model.Usuario;
import edu.mondragon.webengl.seguridad.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public String listarEncuestas(Model model, @AuthenticationPrincipal UsuarioDetails user)
    {
        List<Encuesta> encuestas = encuestaRepo.findAll();
        model.addAttribute("encuestas", encuestas);

        // Mapa para saber si el usuario ha respondido cada encuesta
        Map<Integer, Boolean> respondidas = new HashMap<>();
        if (user != null)
        {
            int usuarioID = user.getUsuario().getUsuarioID();
            for (Encuesta encuesta : encuestas)
            {
                boolean respondida = hacerEncuestaRepo
                    .findFirstByUsuarioIDAndEncuestaIDOrderByEncuestaIDDesc(usuarioID, encuesta.getEncuestaID())
                    .isPresent();
                respondidas.put(encuesta.getEncuestaID(), respondida);
            }
        }
        model.addAttribute("respondidas", respondidas);
        model.addAttribute("tituloPagina", "Encuestas Disponibles");

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

        // Aquí se podría añadir lógica para evitar que un usuario conteste dos veces la misma encuesta

        // Añadir la encuesta y el objeto para respuestas al modelo
        Encuesta encuesta = encuestaOpt.get();
        // Añadir la encuesta al modelo
        model.addAttribute("encuesta", encuesta);
        // Añadir el DTO vacío al modelo para el binding del formulario
        model.addAttribute("encuestaRespuestas", new EncuestaRespuestas());
        model.addAttribute("tituloPagina", "Responder Encuesta");


        return "encuestas/tipos/" + encuesta.getTipoEncuesta();
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

        // he.setFecha(LocalDateTime.now());

        // Crear nueva respuesta de encuesta
        HacerEncuesta he = new HacerEncuesta();
        he.setEncuestaID(encuestaID);
        he.setUsuarioID(user.getUsuario().getUsuarioID());

        // Utilizar encuesta original
        Encuesta encuesta = encuestaOpt.get();

        try
        {
            // Calcular resultados según el tipo de encuesta
            switch(encuesta.getTipoEncuesta())
            {
                case "integracion":
                    calcularResultadosIntegracion(he, encuestaRespuestas);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de encuesta no soportada");
            }
            hacerEncuestaRepo.save(he);
            redirectAttrs.addFlashAttribute("mensaje", "¡Gracias por responder la encuesta!");
            return "redirect:/encuestas/graficoEncuesta";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Error al procesar la encuesta: " + e.getMessage());
            return "redirect:/encuestas";
        }
    }

    private void calcularResultadosIntegracion(HacerEncuesta he, EncuestaRespuestas er)
    {
        // Calcular y setear las puntuaciones 
        double resultadoPsi = er.getPsicologicoConexion() + er.getPsicologicoExtranjero()
                            + er.getPsicologicoDeseoVivir() + er.getPsicologicoAislamiento();
        // he.setResultadoPsicologico(resultadoPsi);

        double resultadoLin = er.getLinguisticoLectura() + er.getLinguisticoConversacion()
                            + er.getLinguisticoEscritura() + er.getLinguisticoEscucha();
        // he.setResultadoLinguistico(resultadoLin);

        double resultadoEco = er.getEconomicoIngreso() + er.getEconomicoSituacion()
                            + er.getEconomicoGasto400() + er.getEconomicoGasto800()
                            + er.getEconomicoGasto8000() + er.getEconomicoGasto40000() + 1
                            + er.getEconomicoSatisfaccion();
        // he.setResultadoEconomico(resultadoEco);
            
        int resultadoPol4 = er.getPoliticoAccionConvencer() + er.getPoliticoAccionInfluirVoto()
                            + er.getPoliticoAccionDeclaracion() + er.getPoliticoAccionDiscusionPublica()
                            + er.getPoliticoAccionContacto() + er.getPoliticoAccionTrabajoPartido()
                            + er.getPoliticoAccionInsignia() + er.getPoliticoAccionFirmaPeticion()
                            + er.getPoliticoAccionManifestacion() + er.getPoliticoAccionBoicot()
                            + er.getPoliticoAccionRecogerFirmas();
            
        double resultadoPol = er.getPoliticoComprension() + er.getPoliticoDiscusion()
                            + er.getPoliticoPartidos() + er.getPoliticoPresidentePartido()
                            + er.getPoliticoSenadoPartido() + Integer.valueOf(er.getPoliticoEdadVoto()) + 1
                            + (resultadoPol4 > 4 ? 5 : (resultadoPol4 == 3 || resultadoPol4 == 4) ? 4 : (resultadoPol4 == 2) ? 3 : (resultadoPol4 == 1) ? 2 : 1);
        // he.setResultadoPolitico(resultadoPol);

        int resultadoSoc3A = (er.getSocialParticipacionGrupoA() == 1 ? 0 : er.getSocialParticipacionGrupoA())
                            * (er.getSocialMiembrosEspanolesGrupoA() == 1 ? 0 : er.getSocialMiembrosEspanolesGrupoA());
        int resultadoSoc3B = (er.getSocialParticipacionGrupoB() == 1 ? 0 : er.getSocialParticipacionGrupoB())
                            * (er.getSocialMiembrosEspanolesGrupoB() == 1 ? 0 : er.getSocialMiembrosEspanolesGrupoB());
        int resultadoSoc3C = (er.getSocialParticipacionGrupoC() == 1 ? 0 : er.getSocialParticipacionGrupoC())
                            * (er.getSocialMiembrosEspanolesGrupoC() == 1 ? 0 : er.getSocialMiembrosEspanolesGrupoC());
        int resultadoSoc3D = (er.getSocialParticipacionGrupoD() == 1 ? 0 : er.getSocialParticipacionGrupoD())
                            * (er.getSocialMiembrosEspanolesGrupoD() == 1 ? 0 : er.getSocialMiembrosEspanolesGrupoD());
        int resultadoSoc3E = (er.getSocialParticipacionGrupoE() == 1 ? 0 : er.getSocialParticipacionGrupoE())
                            * (er.getSocialMiembrosEspanolesGrupoE() == 1 ? 0 : er.getSocialMiembrosEspanolesGrupoE());
        int resultadoSoc3 = Math.max(Math.max(resultadoSoc3A, resultadoSoc3B),Math.max(Math.max(resultadoSoc3C, resultadoSoc3D), resultadoSoc3E));

        double resultadoSoc = er.getSocialCenaEspanoles() + er.getSocialContactosEspanoles()
                            + ((int)Math.ceil((resultadoSoc3 / 25.0) * 4)) + er.getSocialFavoresEspanoles();
        // he.setResultadoSocial(resultadoSoc);

        double resultadoNav = er.getNavegacionalConsultaMedica() + er.getNavegacionalBuscarEmpleo()
                            + er.getNavegacionalAyudaLegal() + er.getNavegacionalConduccionAlcohol()
                            + er.getNavegacionalPagoImpuestos() + er.getNavegacionalFormatoDireccion()
                            + er.getNavegacionalAyudaMedicaCronica() + 1;
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

    }


    @Autowired
    private ConsejoService consejoService;

    @GetMapping("/grafico/{encuestaID}") //graficoEncuesta
    public String mostrarGrafico(@PathVariable("encuestaID") int encuestaID,
                                Model model,
                                @AuthenticationPrincipal UsuarioDetails user) {
        // Obtener los resultados de la última encuesta del usuario
        Optional<HacerEncuesta> encuesta = hacerEncuestaRepo
            .findByUsuarioIDAndEncuestaID(user.getUsuario().getUsuarioID(), encuestaID);
        
        if (encuesta.isPresent())
        {
            HacerEncuesta he = encuesta.get();
            model.addAttribute("encuesta", he);

            // Evaluar nivel general
            ConsejoIntegracion nivelGeneral = consejoService.evaluarNivel(he.getResultadoTotal());
            model.addAttribute("nivelGeneral", nivelGeneral);

            // Debug para verificar valores
            System.out.println("Valores en gráfico:");
            System.out.println("Psicológico: " + he.getResultadoPsicologico());
            System.out.println("Lingüístico: " + he.getResultadoLinguistico());
            System.out.println("Económico: " + he.getResultadoEconomico());
            System.out.println("Político: " + he.getResultadoPolitico());
            System.out.println("Social: " + he.getResultadoSocial());
            System.out.println("Navegacional: " + he.getResultadoNavegacional());

            // Encontrar el área más débil
            Map<String, Double> areas = new HashMap<>();
            areas.put("psicologico", he.getResultadoPsicologico());
            areas.put("linguistico", he.getResultadoLinguistico());
            areas.put("economico", he.getResultadoEconomico());
            areas.put("politico", he.getResultadoPolitico());
            areas.put("social", he.getResultadoSocial());
            areas.put("navegacional", he.getResultadoNavegacional());
            
            String areaDebil = areas.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
                
            if (areaDebil != null)
            {
                model.addAttribute("consejosAreaDebil", 
                    consejoService.obtenerConsejosPorArea(areaDebil, areas.get(areaDebil)));
                model.addAttribute("areaDebil", areaDebil);
            }
        }
        model.addAttribute("tituloPagina", "Resultados de la Encuesta");

        return "encuestas/graficoEncuesta";
    }
}
//{id}/grafico(id=${encuesta.encuestaID})