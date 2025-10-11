package com.reservas.application.service;

import com.reservas.web.dto.habitacion.HabitacionDtoEstadoHabitacionRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HabitacionService{
    HabitacionDtoResponse agregarHabitacion(HabitacionDtoRequest habitacionDto);
    Page<HabitacionDtoResponse> obtenerHabitaciones(Pageable pageable);
    HabitacionDtoResponse obtenerHabitacion(Integer id);
    HabitacionDtoResponse modificarHabitacion(HabitacionDtoRequest habitacionDto,Integer id);
    HabitacionDtoResponse modificarEstadoHabitacion(Integer id, HabitacionDtoEstadoHabitacionRequest estadoHabitacionDto);
    Page<HabitacionDtoResponse> obtenerHabitacionesEstadoHabitacion(Pageable pageable,String estadoHabitacion);
    void eliminarHabitacion(Integer id);

    //ToDo:Declarar mas metodos en habitacion
}
