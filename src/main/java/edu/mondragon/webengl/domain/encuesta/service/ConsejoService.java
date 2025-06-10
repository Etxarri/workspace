package edu.mondragon.webengl.domain.encuesta.service;

import org.springframework.stereotype.Service;
import edu.mondragon.webengl.domain.encuesta.model.ConsejoIntegracion;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConsejoService
{
    private static final Map<String, String[]> CONSEJOS_POR_AREA = new HashMap<>();
    
    static
    {
        CONSEJOS_POR_AREA.put("psicologico", new String[]{
            "Participa en grupos de apoyo para inmigrantes",
            "Busca ayuda profesional si te sientes aislado",
            "Mantén contacto regular con familia y amigos"
        });
        
        CONSEJOS_POR_AREA.put("linguistico", new String[]{
            "Inscríbete en clases de español",
            "Practica el idioma con nativos",
            "Consume medios en español (películas, libros, etc.)"
        });
        
        CONSEJOS_POR_AREA.put("economico", new String[]{
            "Busca asesoramiento financiero",
            "Explora oportunidades de formación profesional",
            "Infórmate sobre ayudas económicas disponibles"
        });
        
        CONSEJOS_POR_AREA.put("politico", new String[]{
            "Infórmate sobre el sistema político español",
            "Participa en asociaciones vecinales",
            "Mantente al día con la actualidad política"
        });
        
        CONSEJOS_POR_AREA.put("social", new String[]{
            "Únete a grupos de interés locales",
            "Participa en eventos comunitarios",
            "Desarrolla una red de contactos diversos"
        });
        
        CONSEJOS_POR_AREA.put("navegacional", new String[]{
            "Familiarízate con los servicios públicos online",
            "Aprende a usar el transporte público",
            "Conoce los recursos administrativos disponibles"
        });
    }

    public ConsejoIntegracion evaluarNivel(double valor)
    {
        if (valor >= 0.9)
        {
            return new ConsejoIntegracion(
                "Excelente",
                "Tu nivel de integración es ejemplar",
                new String[]{"Mantén tu excelente nivel de integración", "Ayuda a otros en su proceso de integración"}
            );
        }
        else if (valor >= 0.7)
        {
            return new ConsejoIntegracion(
                "Alto",
                "Estás bien integrado pero aún hay margen de mejora",
                new String[]{"Identifica áreas específicas para mejorar", "Mantén tus buenos hábitos de integración"}
            );
        }
        else if (valor >= 0.5)
        {
            return new ConsejoIntegracion(
                "Moderado",
                "Tu integración es aceptable pero necesita trabajo",
                new String[]{"Participa más activamente en la comunidad", "Busca oportunidades de mejora"}
            );
        }
        else if (valor >= 0.3)
        {
            return new ConsejoIntegracion(
                "Bajo",
                "Tu nivel de integración necesita mejorar significativamente",
                new String[]{"Busca ayuda profesional", "Establece metas claras de integración"}
            );
        }
        else
        {
            return new ConsejoIntegracion(
                "Ínfimo",
                "Tu integración requiere atención urgente",
                new String[]{"Contacta con servicios de apoyo a inmigrantes", "Establece un plan de integración inmediato"}
            );
        }
    }

    public String[] obtenerConsejosPorArea(String area, double valor)
    {
        if (valor >= 0.7) return new String[]{};
        return CONSEJOS_POR_AREA.getOrDefault(area, new String[]{});
    }
}