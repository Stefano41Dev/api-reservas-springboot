package com.reservas.application.service.Impl;

import com.reservas.application.exception.ErrorNegocio;
import com.reservas.application.mapper.HabitacionMapper;
import com.reservas.application.service.HabitacionService;
import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Habitacion;
import com.reservas.domain.repository.HabitacionRepository;
import com.reservas.web.dto.habitacion.HabitacionDtoEstadoHabitacionRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    @Transactional
    public HabitacionDtoResponse agregarHabitacion(HabitacionDtoRequest habitacionDto) {
        var habitacion = habitacionRepository.save(habitacionMapper.toEntity(habitacionDto));
        return habitacionMapper.toDto(habitacion);
    }
    @Override
    public Page<HabitacionDtoResponse> obtenerHabitaciones(Pageable pageable) {
        Page<Habitacion> habitaciones = habitacionRepository.findAllByActivoTrue(pageable);
        return habitaciones.map(habitacionMapper::toDto);
    }

    @Override
    public HabitacionDtoResponse obtenerHabitacion(Integer id) {
        return habitacionMapper.toDto(habitacionRepository.findByIdHabitacionAndActivoTrue(id)
                .orElseThrow(()->  new ErrorNegocio("No se encontro la habitacion con id "+ id, HttpStatus.NOT_FOUND)));
    }

    @Override
    @Transactional
    public HabitacionDtoResponse modificarHabitacion(HabitacionDtoRequest habitacionDto, Integer id) {
        var habitacionBuscada = habitacionRepository.findById(id)
                .orElseThrow(()-> new ErrorNegocio("No se encontro la habitacion con id "+ id, HttpStatus.NOT_FOUND));
        habitacionBuscada =  habitacionMapper.toEntity(habitacionDto);

        return habitacionMapper.toDto(habitacionRepository.save(habitacionBuscada)) ;
    }

    @Override
    @Transactional
    public HabitacionDtoResponse modificarEstadoHabitacion(Integer id, HabitacionDtoEstadoHabitacionRequest estadoHabitacionDto) {
        var habitacionBuscada = habitacionRepository.findById(id)
                .orElseThrow(()-> new ErrorNegocio("No se encontro la habitacion con id "+ id, HttpStatus.NOT_FOUND));
        habitacionBuscada.setEstadoHabitacion(EstadoHabitacion.valueOf(estadoHabitacionDto.estadoHabitacion().toUpperCase()));
        return habitacionMapper.toDto(habitacionRepository.save(habitacionBuscada));
    }

    @Override
    public Page<HabitacionDtoResponse> obtenerHabitacionesEstadoHabitacion(Pageable pageable, String estadoHabitacion) {
        Page<Habitacion> habitacionesSegunEstado;
        try{
            EstadoHabitacion estadoHabitacionEnum = EstadoHabitacion.valueOf(estadoHabitacion);
            habitacionesSegunEstado = habitacionRepository.findAllByEstadoHabitacion(estadoHabitacionEnum,pageable);
        }catch(IllegalArgumentException  ex){
            throw new ErrorNegocio("El estado" + estadoHabitacion + " no es valido", HttpStatus.BAD_REQUEST);
        }
        return habitacionesSegunEstado.map(habitacionMapper::toDto);
    }

    @Override
    public void eliminarHabitacion(Integer id) {
        //Todo:Implementar el metodo para eliminar una habitacion
    }

}
