package com.reservas.application.service.Impl;

import com.reservas.application.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    @Override
    public void enviarCorreo(String gmail, String subject, String text) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(gmail);
        mensaje.setSubject(subject);
        mensaje.setText(text);

        mailSender.send(mensaje);
    }
}
