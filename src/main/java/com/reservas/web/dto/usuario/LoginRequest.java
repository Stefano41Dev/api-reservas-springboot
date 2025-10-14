package com.reservas.web.dto.usuario;

public record LoginRequest(
        String email,
        String password
) {
}
