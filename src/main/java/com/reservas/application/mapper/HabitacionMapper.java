package com.reservas.application.mapper;

import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Habitacion;
import com.reservas.domain.model.ImagenHabitacion;
import com.reservas.web.dto.habitacion.HabitacionDtoRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoResponse;
import com.reservas.web.dto.habitacion.imagenes.ImagenHabitacionDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper {
    public Habitacion toEntity(HabitacionDtoRequest habitacionDto){
        Habitacion habitacion = Habitacion.builder()
                .numeroHabitacion(habitacionDto.numeroHabitacion())
                .cantidadCamas(habitacionDto.cantidadCamas())
                .tipoHabitacion(habitacionDto.tipoHabitacion())
                .urlImagenPrincipal(habitacionDto.urlImagePrincipal())
                .numeroPiso(habitacionDto.numeroPiso())
                .tarifaDiaria(habitacionDto.tarifaDiaria())
                .descripcion(habitacionDto.descripcion())
                .capacidad(habitacionDto.capacidad())
                .estadoHabitacion(EstadoHabitacion.DISPONIBLE)
                .activo(true)
                .build();

        habitacion.setImagenes(habitacionDto.listaImagenes().stream().map(dto->
                ImagenHabitacion.builder()
                .url(dto.url())
                .habitacion(habitacion)
                .build()).toList());

        return habitacion;
    }
    public HabitacionDtoResponse toDto(Habitacion entity){
        return HabitacionDtoResponse.builder()
                .idHabitacion(entity.getIdHabitacion())
                .numeroHabitacion(entity.getNumeroHabitacion())
                .cantidadCamas(entity.getCantidadCamas())
                .tipoHabitacion(entity.getTipoHabitacion().toString())
                .urlImagePrincipal(entity.getUrlImagenPrincipal())
                .listaImagenes(entity.getImagenes().stream().map(img -> new ImagenHabitacionDtoResponse(img.getUrl())).toList())
                .numeroPiso(entity.getNumeroPiso())
                .tarifaDiaria(entity.getTarifaDiaria())
                .descripcion(entity.getDescripcion())
                .capacidad(entity.getCapacidad())
                .estadoHabitacion(entity.getEstadoHabitacion().toString())
                .build();
    }
}
