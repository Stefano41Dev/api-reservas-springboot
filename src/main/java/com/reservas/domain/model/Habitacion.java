package com.reservas.domain.model;

import com.reservas.domain.model.Enum.EstadoHabitacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "habitaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Integer idHabitacion;
    @Column(name = "tarifa_diaria")
    private double taridaDiaria;
    private String descripcion;
    private int capacidad;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_habitacion")
    private EstadoHabitacion estadoHabitacion;
    @OneToMany(mappedBy = "habitacion")
    private List<Reserva> reservas;
}
