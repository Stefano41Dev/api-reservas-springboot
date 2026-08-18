package com.reservas.web.dto.habitacion;

import lombok.Builder;

@Builder
public record HabitacionDtoResponse (
    Integer idHabitacion,
    int numeroHabitacion,
    int cantidadCamas,
    int numeroPiso,
    String tipoHabitacion,
    Double tarifaDiaria,
    String descripcion,
    int capacidad,
    String estadoHabitacion
) {
}
