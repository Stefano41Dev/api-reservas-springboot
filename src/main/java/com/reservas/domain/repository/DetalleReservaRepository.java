package com.reservas.domain.repository;

import com.reservas.domain.model.DetalleReserva;
import com.reservas.domain.model.Enum.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface DetalleReservaRepository extends JpaRepository<DetalleReserva, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(dt) > 0 THEN TRUE ELSE FALSE END
    FROM DetalleReserva dt
    WHERE dt.habitacion.idHabitacion = :idHabitacion
    AND (
        (:fechaInicio < dt.fechaFin) AND (:fechaFin > dt.fechaInicio)
    )
    """)
    boolean verificarHabitacionReservadaRangoFecha(
            @Param("idHabitacion") Integer idHabitacion,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

}
