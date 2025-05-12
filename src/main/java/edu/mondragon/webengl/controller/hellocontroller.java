package edu.mondragon.webengl.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String kaixo() {
        return "Kaixo Mundua!";
    }
}

