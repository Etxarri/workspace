package edu.mondragon.webengl.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.user.model.PasswordResetTokenStore;
import edu.mondragon.webengl.domain.user.service.EmailService;
import jakarta.enterprise.inject.Model;


@Controller
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenStore tokenStore;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        String token = UUID.randomUUID().toString();
        tokenStore.storeToken(email, token);

        emailService.sendPasswordResetEmail(email, token);

        redirectAttributes.addFlashAttribute("message", "Se ha enviado un enlace de recuperación a tu correo.");
        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        ((RedirectAttributes) model).addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        String email = tokenStore.getEmailByToken(token);
        if (email == null) {
            redirectAttributes.addFlashAttribute("error", "Token inválido o expirado.");
            return "redirect:/forgot-password";
        }

        // Aquí deberías actualizar la contraseña real en tu BD
        System.out.println("Nueva contraseña para " + email + ": " + password);

        redirectAttributes.addFlashAttribute("message", "Tu contraseña ha sido restablecida.");
        return "redirect:/login";
    }
}

