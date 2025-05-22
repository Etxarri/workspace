package edu.mondragon.webengl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ForgotPasswordController {

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Aquí deberías buscar el usuario por email y enviar el correo con el enlace de recuperación
        // Por ejemplo: userService.sendPasswordResetEmail(email);

        // Simulación de éxito
        redirectAttributes.addFlashAttribute("message", "Se ha enviado un enlace de recuperación a tu correo.");
        return "redirect:/login";
    }
}
