package com.reservas.web.dto.usuario;

public record RegisterRequest(
        String email,
        String dni,
        String password
) {
}
