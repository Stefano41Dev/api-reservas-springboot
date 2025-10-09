package com.reservas.web.dto.usuario;

public record UsuarioDtoRequest(
        String nombres,
        String apellidos,
        String dni,
        String password
) {
}
