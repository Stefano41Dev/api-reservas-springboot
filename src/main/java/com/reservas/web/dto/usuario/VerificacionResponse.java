package com.reservas.web.dto.usuario;

import lombok.Builder;

@Builder
public record VerificacionResponse(
        Boolean success,
        String message
) {
}
