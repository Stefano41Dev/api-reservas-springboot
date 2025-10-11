package com.reservas.domain.repository;

import com.reservas.domain.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findAllByActivoTrue(Pageable pageable);
    Optional<Reserva> findByIdReservaAndActivoTrue(Long id);
}
