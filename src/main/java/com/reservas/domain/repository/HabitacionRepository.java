package com.reservas.domain.repository;

import com.reservas.domain.model.Habitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

    Page<Habitacion> findAllByActivoTrue(Pageable pageable);
}

