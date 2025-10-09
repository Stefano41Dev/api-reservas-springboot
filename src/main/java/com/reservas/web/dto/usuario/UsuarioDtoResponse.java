package com.reservas.web.dto.usuario;

import java.time.LocalDate;

public record UsuarioDtoResponse(
        Long idUsuario,
        String nombres,
        String apellidos,
        String dni,
        LocalDate fechaRegistro,
        String role
) {
}
