package com.reservas.domain.repository;

import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Habitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    Page<Habitacion> findAllByActivoTrueAndEstadoHabitacion(EstadoHabitacion estadoHabitacion, Pageable pageable);
    Optional<Habitacion> findByIdHabitacionAndActivoTrue(Integer idHabitacion);

    Page<Habitacion>findAllByEstadoHabitacion(EstadoHabitacion estadoHabitacion, Pageable pageable);
}

