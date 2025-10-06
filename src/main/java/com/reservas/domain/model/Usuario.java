package com.reservas.domain.model;

import com.reservas.domain.model.Enum.RolesUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String nombres;
    private String apellidos;
    private String dni;
    private String password;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    @Enumerated(EnumType.STRING)
    private RolesUsuario role;
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;
}
