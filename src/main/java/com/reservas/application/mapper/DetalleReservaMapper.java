package com.reservas.application.mapper;

import com.reservas.domain.model.DetalleReserva;
import com.reservas.domain.model.Enum.EstadoReserva;
import com.reservas.domain.model.Habitacion;
import com.reservas.domain.model.Reserva;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class DetalleReservaMapper {
    public DetalleReservaDtoResponse toDto (DetalleReserva detalleReserva){
        return DetalleReservaDtoResponse.builder()
                .idDetalleReserva(detalleReserva.getIdDetalleReserva())
                .idReserva(detalleReserva.getReserva().getIdReserva())
                .idHabitacion(detalleReserva.getHabitacion().getIdHabitacion())
                .dias(detalleReserva.getDias())
                .fechaInicio(detalleReserva.getFechaInicio())
                .fechaFin(detalleReserva.getFechaFin())
                .montoTotalHabitacion(detalleReserva.getMontoTotalHabitacion())
                .estadoReserva(detalleReserva.getEstadoReserva().toString())
                .build();
    }
    public DetalleReserva toEntity (DetalleReservaDtoRequests detalleReserva, Reserva reserva, Habitacion habitacion, Long dias)  {
        return DetalleReserva.builder()
                .reserva(reserva)
                .habitacion(habitacion)
                .dias(dias)
                .fechaInicio(detalleReserva.fechaInicio())
                .fechaFin(detalleReserva.fechaFin())
                .montoTotalHabitacion(0.0)
                .estadoReserva(EstadoReserva.PENDIENTE)
                .build();
    }
}
