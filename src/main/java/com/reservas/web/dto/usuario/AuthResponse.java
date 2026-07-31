package com.reservas.web.dto.usuario;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String refreshToken
) {

}
