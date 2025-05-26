package edu.mondragon.webengl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.user.model.PasswordResetTokenStore;
import edu.mondragon.webengl.domain.user.model.User;
import edu.mondragon.webengl.domain.user.repository.UserRepository;
import edu.mondragon.webengl.domain.user.service.EmailService;


@Controller
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenStore tokenStore;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        java.util.Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No se encontró una cuenta con ese correo.");
            return "redirect:/forgot-password";
        }

        String code = tokenStore.generateAndStoreCode(email);
        emailService.sendVerificationCode(email, code);

        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/verify-code";
    }

    @GetMapping("/verify-code")
    public String showVerifyCodePage() {
        return "verifyCode";
    }

    @PostMapping("/verify-code")
    public String verifyCode(
            @RequestParam("email") String email,
            @RequestParam("code") String code,
            RedirectAttributes redirectAttributes) {

        if (tokenStore.verifyCode(email, code)) {
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/reset-password";
        } else {
            redirectAttributes.addFlashAttribute("error", "Código inválido o expirado.");
            return "redirect:/verify-code";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("email", email);
        return "resetPassword";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes) {

        java.util.Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            System.out.println("Error");
            return "redirect:/forgot-password";
        }

        User user = userOpt.get();
        user.setPassword(password); 
        userRepository.updateUser(user);
        tokenStore.removeCode(email);

        redirectAttributes.addFlashAttribute("message", "Tu contraseña ha sido restablecida.");
        return "redirect:/login";
    }
    
}



