package edu.mondragon.webengl.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.mondragon.webengl.domain.pais.repository.CiudadRepository;
import edu.mondragon.webengl.domain.pais.repository.PaisRepository;
import edu.mondragon.webengl.domain.user.model.PasswordResetTokenStore;
import edu.mondragon.webengl.domain.user.model.Usuario;

import edu.mondragon.webengl.domain.user.repository.UsuarioRepository;
import edu.mondragon.webengl.domain.user.service.EmailService;
import edu.mondragon.webengl.domain.user.service.UsuarioService;


@Controller
public class ForgotPasswordController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenStore tokenStore;

    private final UsuarioService usuarioService;

    //private UsuarioRepository userRepository;

    public ForgotPasswordController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        Optional<Usuario> user = usuarioService.existeUsuarioPorEmail(email)?
                usuarioService.findUsuarioByEmail(email) :
                Optional.empty();

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

        java.util.Optional<Usuario> userOpt = usuarioService.findUsuarioByEmail(email);
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            System.out.println("Error");
            return "redirect:/forgot-password";
        }

        Usuario user = userOpt.get();
        user.setContraseña(usuarioService.encriptarContraseña(password));
        usuarioService.guardarUsuario(user);
        tokenStore.removeCode(email);

        redirectAttributes.addFlashAttribute("message", "Tu contraseña ha sido restablecida.");
        return "redirect:/login";
    }
    
}



