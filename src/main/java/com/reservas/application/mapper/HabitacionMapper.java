package com.reservas.application.mapper;

import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Enum.TipoHabitacion;
import com.reservas.domain.model.Habitacion;
import com.reservas.web.dto.habitacion.HabitacionDtoRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {
    public Habitacion toEntity(HabitacionDtoRequest habitacionDto){
        return Habitacion.builder()
                .numeroHabitacion(habitacionDto.numeroHabitacion())
                .cantidadCamas(habitacionDto.cantidadCamas())
                .tipoHabitacion(habitacionDto.tipoHabitacion())
                .numeroPiso(habitacionDto.numeroPiso())
                .tarifaDiaria(habitacionDto.tarifaDiaria())
                .descripcion(habitacionDto.descripcion())
                .capacidad(habitacionDto.capacidad())
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .activo(true)
                .build();
    }
    public HabitacionDtoResponse toDto(Habitacion entity){
        return HabitacionDtoResponse.builder()
                .idHabitacion(entity.getIdHabitacion())
                .numeroHabitacion(entity.getNumeroHabitacion())
                .cantidadCamas(entity.getCantidadCamas())
                .tipoHabitacion(entity.getTipoHabitacion().toString())
                .numeroPiso(entity.getNumeroPiso())
                .tarifaDiaria(entity.getTarifaDiaria())
                .descripcion(entity.getDescripcion())
                .capacidad(entity.getCapacidad())
                .estadoHabitacion(entity.getEstadoHabitacion().toString())
                .build();
    }
}
