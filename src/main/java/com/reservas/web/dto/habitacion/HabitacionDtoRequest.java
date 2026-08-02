package com.reservas.web.dto.habitacion;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record HabitacionDtoRequest(
        @Positive(message = "La tarifa no puede ser negativa")
        Double tarifaDiaria,
        String descripcion,
        @Positive(message = "La capacidad no puede ser negativa")
        Integer capacidad
) {
}
