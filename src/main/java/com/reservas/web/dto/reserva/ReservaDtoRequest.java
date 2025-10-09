package com.reservas.web.dto.reserva;

import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;

import java.util.List;

public record ReservaDtoRequest(
        Long idUsuario,
        List<DetalleReservaDtoRequests> reservaDetalleDtoRequests
) {
}
