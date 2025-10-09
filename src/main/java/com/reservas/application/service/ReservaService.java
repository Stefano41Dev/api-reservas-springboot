package com.reservas.application.service;

import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;

public interface ReservaService {
    ReservaDtoResponse agregarReserva(ReservaDtoRequest reservaDtoRequest);
    //ToDo:Declarar los metodos para la interface de reserva
}
