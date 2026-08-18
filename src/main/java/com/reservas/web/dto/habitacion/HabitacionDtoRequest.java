package com.reservas.web.dto.habitacion;

import com.reservas.domain.model.Enum.TipoHabitacion;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record HabitacionDtoRequest(
        @Positive(message = "Eliga un numero de habitacion correcto")
        int numeroHabitacion,
        @Positive(message = "Eliga una cantidad de camas")
        int cantidadCamas,
        @Positive(message = "Eliga un piso existente")
        int numeroPiso,
        TipoHabitacion tipoHabitacion,
        @Positive(message = "La tarifa no puede ser negativa")
        Double tarifaDiaria,
        String descripcion,
        @Positive(message = "La capacidad no puede ser negativa")
        Integer capacidad
) {
}
