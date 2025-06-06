package edu.mondragon.webengl.controller;

import edu.mondragon.webengl.internationalization.service.DeepLService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DeepLRestController {

    private final DeepLService deepLService;

    public DeepLRestController(DeepLService deepLService) {
        this.deepLService = deepLService;
    }

    @PostMapping("/traducir")
    public Map<String, String> traducirPregunta(@RequestBody Map<String, String> body) {
        String texto = body.get("texto");
        String lang = body.get("lang");
        String traducido = deepLService.traducir(texto, lang);
        return Map.of("traduccion", traducido);
    }
}