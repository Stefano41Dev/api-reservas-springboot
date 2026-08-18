package com.reservas.web.dto.habitacion;

import com.reservas.web.dto.habitacion.imagenes.ImagenHabitacionDtoResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record HabitacionDtoResponse (
    Integer idHabitacion,
    int numeroHabitacion,
    int cantidadCamas,
    int numeroPiso,
    String tipoHabitacion,
    String urlImagePrincipal,
    List<ImagenHabitacionDtoResponse> listaImagenes,
    Double tarifaDiaria,
    String descripcion,
    int capacidad,
    String estadoHabitacion
) {
}
