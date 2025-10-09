package com.reservas.web.dto.reserva.detalle;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record DetalleReservaDtoResponse(
        Long idDetalleReserva,
        Long idReserva,
        Integer idHabitacion,
        Long dias,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        double montoTotalHabitacion,
        String estadoReserva
) {
}
