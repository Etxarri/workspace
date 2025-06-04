package edu.mondragon.webengl.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerIdiomas {
    @ModelAttribute("banderas")
    public List<Map<String, String>> getBanderas() {
        return List.of(
            Map.of("src", "/images/español_logo.png", "alt", "Español", "lang", "es"),
            Map.of("src", "/images/frances_logo.png", "alt", "Francés", "lang", "fr"),
            Map.of("src", "/images/ingles_logo.png", "alt", "Inglés", "lang", "en"),
            Map.of("src", "/images/arabe_logo.png", "alt", "arabe", "lang", "ar")
        );
    }
}