package com.reservas.web.dto.habitacion;

import lombok.Builder;

@Builder
public record HabitacionDtoResponse (
    Integer idHabitacion,
    Double tarifaDiaria,
    String descripcion,
    int capacidad,
    String estadoHabitacion
) {
}
