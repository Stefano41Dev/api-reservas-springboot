package com.reservas.application.service.Impl;

import com.reservas.application.service.UsuarioService;
import com.reservas.domain.repository.UsuarioRepository;
import com.reservas.web.dto.usuario.UsuarioDtoRequest;
import com.reservas.web.dto.usuario.UsuarioDtoResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDtoResponse agregarNuevoUsuario(UsuarioDtoRequest usuarioDtoRequest) {
        return null;
    }

    @Override
    public UsuarioDtoResponse actualizarUsuario(UsuarioDtoRequest usuarioDtoRequest) {
        return null;
    }

    @Override
    public Page<UsuarioDtoResponse> listarUsuarios(Pageable pageable) {
        return null;
    }
}
