package edu.mondragon.webengl.internationalization.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

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

        String body = "text=" + texto + "&target_lang=" + targetLang.toUpperCase();

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, String>> translations = (List<Map<String, String>>) response.getBody().get("translations");
            return translations.get(0).get("text");
        }
        return texto;
    }

    private DeepLService deepLService;

    @GetMapping("/ejemplo-traduccion")
    public String ejemploTraduccion(@RequestParam(defaultValue = "es") String lang, Model model) {
        String textoOriginal = "Bienvenido a Welco";
        String textoTraducido = deepLService.traducir(textoOriginal, lang);
        model.addAttribute("mensaje", textoTraducido);
        return "ejemploVista";
    }
}