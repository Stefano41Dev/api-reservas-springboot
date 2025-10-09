package com.reservas.web.controller;

import com.reservas.application.service.ReservaService;
import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reserva")
public class ReservaController {
    private final ReservaService reservaService;
    @PostMapping
    public ResponseEntity<ReservaDtoResponse> agregarReserva(@RequestBody ReservaDtoRequest reservaDtoRequest){
        ReservaDtoResponse reservaDtoResponse = reservaService.agregarReserva(reservaDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaDtoResponse);
    }
}

