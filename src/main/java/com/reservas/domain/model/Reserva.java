package com.reservas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Column(name = "monto_total_reservas")
    private double montoTotalReservas;

    private Boolean activo;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleReserva> detalleReservas = new ArrayList<>();

    public void addDetalleReserva(DetalleReserva detalleReserva) {
        this.detalleReservas.add(detalleReserva);
        detalleReserva.setReserva(this);
    }
}
