package com.reservas.web.dto.reserva.detalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record   DetalleReservaDtoRequests(
   //Long idReserva,
   @NotBlank
   Integer idHabitacion,
   @NotNull(message = "La fecha de inicio es obligatoria")
   @JsonFormat(pattern = "yyyy-MM-dd")
   LocalDate fechaInicio,
   @NotNull(message = "La fecha fin es obligatoria")
   @JsonFormat(pattern = "yyyy-MM-dd")
   LocalDate fechaFin

) {
}
