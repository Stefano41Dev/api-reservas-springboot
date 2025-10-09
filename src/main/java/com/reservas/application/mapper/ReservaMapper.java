package com.reservas.application.mapper;

import com.reservas.domain.model.Reserva;
import com.reservas.domain.model.Usuario;
import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaMapper {
    private final DetalleReservaMapper detalleReservaMapper;

    public ReservaDtoResponse toDto(Reserva reserva) {
        return ReservaDtoResponse.builder()
                .idReserva(reserva.getIdReserva())
                .idUsuario(reserva.getUsuario().getIdUsuario())
                .montoTotalReservas(reserva.getMontoTotalReservas())
                .detallesReserva(reserva.getDetalleReservas().stream().map(detalleReservaMapper::toDto).toList())
                .build();
    }
    public Reserva toEntity(ReservaDtoRequest reservaDtoRequest, Usuario usuario, double montoTotalReservas) {
        return Reserva.builder()
                .usuario(usuario)
                .montoTotalReservas(montoTotalReservas)
                .build();
    }
}
