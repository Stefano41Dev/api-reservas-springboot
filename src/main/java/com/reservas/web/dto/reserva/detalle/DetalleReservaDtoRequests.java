package com.reservas.web.dto.reserva.detalle;

import java.time.LocalDate;

public record   DetalleReservaDtoRequests(
   //Long idReserva,
   Integer idHabitacion,
   LocalDate fechaInicio,
   LocalDate fechaFin

) {
}
