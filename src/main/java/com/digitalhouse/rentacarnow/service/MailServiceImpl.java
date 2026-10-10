package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final String from;
    private final String fromName;
    private final boolean enabled;

    public MailServiceImpl(JavaMailSender mailSender,
                           @Value("${app.mail.from}") String from,
                           @Value("${app.mail.from-name}") String fromName,
                           @Value("${app.mail.enabled}") boolean enabled) {
        this.mailSender = mailSender;
        this.from = from;
        this.fromName = fromName;
        this.enabled = enabled;
    }

    @Override
    public void sendWelcome(User user) {
        String subject = "Bienvenido a " + fromName;
        String text = "Hola " + user.getName() + ",\n\n"
                + "Tu cuenta en " + fromName + " fue creada con éxito.\n"
                + ("OWNER".equals(user.getRoleName()) && !Boolean.TRUE.equals(user.getVerified())
                        ? "Tu cuenta de OWNER está pendiente de verificación por un administrador.\n"
                        : "")
                + "\nSaludos,\n" + fromName;
        send(user.getEmail(), subject, text);
    }

    @Override
    public void sendPasswordReset(User user, String resetLink) {
        String subject = "Recuperá tu contraseña - " + fromName;
        String text = "Hola " + user.getName() + ",\n\n"
                + "Pediste restablecer tu contraseña. Usá este enlace (válido por 1 hora):\n"
                + resetLink + "\n\n"
                + "Si no fuiste vos, ignorá este mensaje.\n\nSaludos,\n" + fromName;
        send(user.getEmail(), subject, text);
    }

    private void send(String to, String subject, String text) {
        if (!enabled) {
            log.warn("Mail deshabilitado (app.mail.enabled=false), no se envía a {}", to);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromName + " <" + from + ">");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("No se pudo enviar mail a {}: {}", to, e.getMessage());
            throw new IllegalStateException("No se pudo enviar el mail.");
        }
    }
}
