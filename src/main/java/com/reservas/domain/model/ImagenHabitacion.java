package com.reservas.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagenes_habitacion")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImagenHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagen;
    private String url;
    @ManyToOne
    @JoinColumn(name = "id_habitacion")
    private Habitacion habitacion;
}
