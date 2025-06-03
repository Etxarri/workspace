package edu.mondragon.webengl.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String to, String code)
    {
        try
        {
            String subject = "Código de recuperación de contraseña";
            String body = "Tu código de recuperación es: " + code + "\nEste código expirará en 15 minutos.";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("✅ Código enviado a: " + to);
        }
        catch (Exception e)
        {
            System.err.println("❌ Error al enviar el correo: " + e.getMessage());
        }
    }
}