package com.reservas.application.mapper;

import com.reservas.domain.model.Enum.RolesUsuario;
import com.reservas.domain.model.Usuario;
import com.reservas.web.dto.usuario.RegisterRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AuthenticationMapper {
    public Usuario toEntity (RegisterRequest registerRequest) {
        return Usuario.builder()
                .email(registerRequest.email())
                .fechaRegistro(LocalDate.now())
                .dni(registerRequest.dni())
                .role(RolesUsuario.CLIENTE)
                .build();
    }
}
