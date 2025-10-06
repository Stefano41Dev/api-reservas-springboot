package com.reservas.domain.model;

import com.reservas.domain.model.Enum.EstadoPago;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;
    @OneToOne(fetch = FetchType.LAZY)
    private Reserva reserva;
    @Column(name = "monto_total")
    private double montoTotal;
    @Column(name = "estado_pago")
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;
    @Column(name = "fecha_pago")
    private LocalDate fechaPago;
}
