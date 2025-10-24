package com.reservas.application.service.Impl;

import com.reservas.application.config.security.JwtService;
import com.reservas.application.mapper.AuthenticationMapper;
import com.reservas.application.service.AuthenticationService;
import com.reservas.domain.model.Usuario;
import com.reservas.domain.repository.UsuarioRepository;
import com.reservas.web.dto.usuario.AuthResponse;
import com.reservas.web.dto.usuario.LoginRequest;
import com.reservas.web.dto.usuario.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationMapper authenticationMapper;

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        Usuario usuario = authenticationMapper.toEntity(registerRequest);
        usuario.setPassword(passwordEncoder.encode(registerRequest.password()));
        usuario =  usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        Usuario user = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado después de autenticación exitosa."));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token inválido o ausente");
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        Usuario user = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (jwtService.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            return AuthResponse.builder()
                    .token(newAccessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        throw new RuntimeException("Refresh token expirado o inválido");
    }


}
