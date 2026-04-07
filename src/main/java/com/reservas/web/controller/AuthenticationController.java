package com.reservas.web.controller;

import com.reservas.application.service.AuthenticationService;
import com.reservas.web.dto.usuario.AuthResponse;
import com.reservas.web.dto.usuario.LoginRequest;
import com.reservas.web.dto.usuario.RegisterRequest;
import com.reservas.web.dto.usuario.VerificacionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<VerificacionResponse> register(@RequestBody RegisterRequest request) {
        VerificacionResponse verificacionResponse = authenticationService.register(request);
        return ResponseEntity.ok(verificacionResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<AuthResponse> verification(@RequestParam String email, @RequestParam String codigo) {
        return ResponseEntity.ok(authenticationService.verify(email,codigo));
    }
}
