package com.reservas.domain.model;

import com.reservas.domain.model.Enum.EstadoReserva;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "reserva_habitacion")
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleReserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_reserva")
    private Long idDetalleReserva;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
    @ManyToOne(fetch = FetchType.LAZY)
    private Habitacion habitacion;
    private Long dias;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    @Column(name = "monto_total_habitacion")
    private double montoTotalHabitacion;
    @Column(name = "estado_reserva")
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;


}
