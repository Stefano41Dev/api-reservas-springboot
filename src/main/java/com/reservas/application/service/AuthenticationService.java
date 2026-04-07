package com.reservas.application.service;

import com.reservas.web.dto.usuario.AuthResponse;
import com.reservas.web.dto.usuario.LoginRequest;
import com.reservas.web.dto.usuario.RegisterRequest;
import com.reservas.web.dto.usuario.VerificacionResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    VerificacionResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(HttpServletRequest request);
    AuthResponse verify(String email, String codigo);
}
