package com.reservas.web.dto.habitacion;

import lombok.Builder;

@Builder
public record HabitacionDtoRequest(
        Double tarifaDiaria,
        String descripcion,
        Integer capacidad
) {
}
