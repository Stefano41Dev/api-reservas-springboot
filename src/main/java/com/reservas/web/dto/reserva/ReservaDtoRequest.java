package com.reservas.web.dto.reserva;

import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ReservaDtoRequest(
        @NotBlank
        Long idUsuario,
        @NotBlank
        List<DetalleReservaDtoRequests> reservaDetalleDtoRequests
) {
}
