package com.reservas.application.mapper;

import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Habitacion;
import com.reservas.web.dto.habitacion.HabitacionDtoRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {
    public Habitacion toEntity(HabitacionDtoRequest habitacionDto){
        return Habitacion.builder()
                .tarifaDiaria(habitacionDto.tarifaDiaria())
                .descripcion(habitacionDto.descripcion())
                .capacidad(habitacionDto.capacidad())
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .build();
    }
    public HabitacionDtoResponse toDto(Habitacion entity){
        return HabitacionDtoResponse.builder()
                .idHabitacion(entity.getIdHabitacion())
                .tarifaDiaria(entity.getTarifaDiaria())
                .descripcion(entity.getDescripcion())
                .capacidad(entity.getCapacidad())
                .estadoHabitacion(entity.getEstadoHabitacion().toString())
                .build();
    }
}
