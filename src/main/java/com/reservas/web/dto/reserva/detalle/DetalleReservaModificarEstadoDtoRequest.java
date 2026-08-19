package com.reservas.web.dto.reserva.detalle;

import com.reservas.domain.model.Enum.EstadoReserva;

public record DetalleReservaModificarEstadoDtoRequest(
        EstadoReserva estadoReserva
) {
}
