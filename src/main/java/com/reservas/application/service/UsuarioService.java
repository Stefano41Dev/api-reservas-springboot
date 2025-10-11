package com.reservas.application.service;

import com.reservas.web.dto.usuario.UsuarioDtoRequest;
import com.reservas.web.dto.usuario.UsuarioDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    UsuarioDtoResponse agregarNuevoUsuario(UsuarioDtoRequest usuarioDtoRequest);
    UsuarioDtoResponse actualizarUsuario(UsuarioDtoRequest usuarioDtoRequest);
    Page<UsuarioDtoResponse> listarUsuarios(Pageable pageable);
}
