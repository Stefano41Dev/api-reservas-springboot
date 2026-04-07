package com.reservas.application.service;

public interface IEmailService {
    void enviarCorreo(String gmail, String subject, String text);
}
