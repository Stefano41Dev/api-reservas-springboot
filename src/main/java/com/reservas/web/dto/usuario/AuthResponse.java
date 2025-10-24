package com.reservas.web.dto.usuario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String refreshToken;
}
