package com.reservas.web.dto.habitacion;

import jakarta.validation.constraints.NotBlank;

public record HabitacionDtoEstadoHabitacionRequest (
        @NotBlank
        String estadoHabitacion
){
}
