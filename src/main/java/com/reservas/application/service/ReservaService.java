package com.reservas.application.service;

import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaModificarEstadoDtoRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservaService {
    ReservaDtoResponse agregarReserva(ReservaDtoRequest reservaDtoRequest);
    ReservaDtoResponse modificarReserva(ReservaDtoRequest reservaDtoRequest, Long idReserva);
    DetalleReservaDtoResponse modificarEstadoDetalleReserva(DetalleReservaModificarEstadoDtoRequest reservaDtoRequest, Long idDetalleReserva);
    ReservaDtoResponse buscarReservaPorId(Long idReserva);
    Page<ReservaDtoResponse> listaReservas(Pageable pageable);
}
