package edu.mondragon.webengl.internationalization.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class DeepLService {

    @Value("${deepl.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api-free.deepl.com/v2/translate";

    public String traducir(String texto, String targetLang) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "DeepL-Auth-Key " + apiKey);

        String encodedText = URLEncoder.encode(texto, StandardCharsets.UTF_8);
        String body = "text=" + encodedText + "&target_lang=" + targetLang.toUpperCase();

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object translationsObj = response.getBody().get("translations");
                if (translationsObj instanceof List) {
                    List translations = (List) translationsObj;
                    if (!translations.isEmpty() && translations.get(0) instanceof Map) {
                        Map first = (Map) translations.get(0);
                        Object textObj = first.get("text");
                        if (textObj != null) {
                            return textObj.toString();
                        }
                    }
                }
            } else {
                System.err.println("Respuesta de DeepL no OK: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error al traducir con DeepL: " + e.getMessage());
        }
        return texto;
    }

/*
    private DeepLService deepLService;

    @GetMapping("/ejemplo-traduccion")
    public String ejemploTraduccion(@RequestParam(defaultValue = "es") String lang, Model model) {
        String textoOriginal = "Bienvenido a Welco";
        String textoTraducido = deepLService.traducir(textoOriginal, lang);
        model.addAttribute("mensaje", textoTraducido);
        return "ejemploVista";
    }
    */
}