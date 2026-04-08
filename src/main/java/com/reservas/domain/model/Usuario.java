package com.reservas.domain.model;

import com.reservas.domain.model.Enum.RolesUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String nombres;
    private String apellidoMaterno;
    private String apellidoPaterno;
    @Column(unique = true)
    private String dni;
    private Integer codigoVerificacionDni;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    private Boolean activo = false;
    private String codigoVerificacion;
    private LocalDateTime codeExpiration;
    @Enumerated(EnumType.STRING)
    private RolesUsuario role;
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
