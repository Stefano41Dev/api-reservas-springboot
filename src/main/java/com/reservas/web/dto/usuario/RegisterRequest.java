package com.reservas.web.dto.usuario;

public record RegisterRequest(
        String nombres,
        String apellidos,
        String email,
        String dni,
        String password
) {
}
