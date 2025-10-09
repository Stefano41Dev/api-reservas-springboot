package com.reservas.web.dto.reserva;

import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ReservaDtoResponse(
        Long idReserva,
        Long idUsuario,
        double montoTotalReservas,
        List<DetalleReservaDtoResponse> detallesReserva
) {
}
