package com.reservas.domain.model;

import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Enum.TipoHabitacion;
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
    @Column(name = "numero_habitacion")
    private int numeroHabitacion;
    @Column(name = "tarifa_diaria")
    private Double tarifaDiaria;
    private String descripcion;
    @Column(name = "cantidad_camas")
    private int cantidadCamas;
    @Column(name = "numero_piso")
    private int numeroPiso;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_habitacion")
    private TipoHabitacion tipoHabitacion;
    private Integer capacidad;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_habitacion")
    private EstadoHabitacion estadoHabitacion;
    private Boolean activo;
    @OneToMany(mappedBy = "habitacion")
    private List<DetalleReserva> reservasHabitacion;
}
