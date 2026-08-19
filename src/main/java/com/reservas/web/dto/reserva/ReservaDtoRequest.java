package com.reservas.web.dto.reserva;

import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ReservaDtoRequest(
        @Positive
        Long idUsuario,
        List<DetalleReservaDtoRequests> reservaDetalleDtoRequests
) {
}
