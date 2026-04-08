package com.reservas.application.service.Impl;

import com.reservas.application.config.security.JwtService;
import com.reservas.application.exception.ErrorNegocio;
import com.reservas.application.mapper.AuthenticationMapper;
import com.reservas.application.service.AuthenticationService;
import com.reservas.application.client.IDniClient;
import com.reservas.application.service.IEmailService;
import com.reservas.domain.model.Usuario;
import com.reservas.domain.repository.UsuarioRepository;
import com.reservas.web.dto.dni.DniDtoResponse;
import com.reservas.web.dto.usuario.AuthResponse;
import com.reservas.web.dto.usuario.LoginRequest;
import com.reservas.web.dto.usuario.RegisterRequest;
import com.reservas.web.dto.usuario.VerificacionResponse;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${jsonpe.bearerToken}")
    private String bearerToken;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationMapper authenticationMapper;
    private final IDniClient dniClient;
    private final IEmailService emailService;
    @Override
    public VerificacionResponse register(RegisterRequest registerRequest) {

        if(usuarioRepository.existsByDni(registerRequest.dni())){
            throw new ErrorNegocio("El dni " + registerRequest.dni() + " ya esta registrado", HttpStatus.CONFLICT);
        }
        if(usuarioRepository.existsByEmail(registerRequest.email())){
            throw new ErrorNegocio("El email " + registerRequest.email() + " ya esta registrado", HttpStatus.CONFLICT);
        }

        DniDtoResponse dniDtoResponse;
        try{
            dniDtoResponse = dniClient.consultarDni(
                     "Bearer " + bearerToken,
                            Map.of("dni", registerRequest.dni())
            );
        }catch (FeignException e){
            System.out.println();
            throw new ErrorNegocio("No se encontro datos con el siguiente dni: "+ registerRequest.dni(), HttpStatus.NOT_FOUND);
        }


        Usuario usuario = authenticationMapper.toEntity(registerRequest);
        if(dniDtoResponse!=null){
            usuario.setNombres(dniDtoResponse.data().nombres());
            usuario.setApellidoPaterno(dniDtoResponse.data().apellidoPaterno());
            usuario.setApellidoMaterno(dniDtoResponse.data().apellidoMaterno());
            usuario.setCodigoVerificacionDni(dniDtoResponse.data().codigoVerificacionDni());
        }
        usuario.setPassword(passwordEncoder.encode(registerRequest.password()));
        String codigoVerificacion = generarCodigo();
        usuario.setCodigoVerificacion(codigoVerificacion);
        usuario.setCodeExpiration(LocalDateTime.now().plusMinutes(10));

        usuario = usuarioRepository.save(usuario);


        String link = "http://localhost:8080/auth/verify?email="
                + usuario.getEmail() + "&codigo=" + codigoVerificacion;

        emailService.enviarCorreo(usuario.getEmail(), "HOTEL EL CISNE - ACTIVAR CUENTA","Dale Click al siguinte enlace para verificar tu cuenta " + link);

        return VerificacionResponse.builder()
                .success(true)
                .message("Revise su correo para verificar la cuenta")
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

    @Override
    public AuthResponse verify(String email, String codigo) {
        Usuario usuario =usuarioRepository.findByEmail(email).orElseThrow(()-> new ErrorNegocio("No se encontro el email "+ email,HttpStatus.NOT_FOUND));
        if (usuario.getCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new ErrorNegocio("Codigo Expirado", HttpStatus.CONFLICT);
        }

        if (!usuario.getCodigoVerificacion().equals(codigo)) {
            throw new ErrorNegocio("Codigo Incorrecto", HttpStatus.CONFLICT);

        }
        usuario.setActivo(true);
        usuario.setCodigoVerificacion(null);

        String jwtToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateRefreshToken(usuario);

        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public String generarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}
