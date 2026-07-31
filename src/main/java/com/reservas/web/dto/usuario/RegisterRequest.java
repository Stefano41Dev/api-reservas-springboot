package com.reservas.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        String nombres,
        @NotBlank
        String apellidos,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(max = 8, min = 8, message = "El DNI debe tener 8 caracteres")
        String dni,
        @NotBlank
        String password
) {
}
