package com.reservas.web.controller;

import com.reservas.application.service.ReservaService;
import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaModificarEstadoDtoRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDtoResponse> buscarReservaPorId(@PathVariable Long id){
        ReservaDtoResponse reservaDtoResponse = reservaService.buscarReservaPorId(id);
        return ResponseEntity.ok(reservaDtoResponse);
    }
    @GetMapping
    public ResponseEntity<Page<ReservaDtoResponse>> listaReservas(@PageableDefault Pageable pageable){
        var reservas = reservaService.listaReservas(pageable);
        return ResponseEntity.ok(reservas);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DetalleReservaDtoResponse> modificarEstadoDetalleReserva (
            @PathVariable Long id,
            @RequestBody DetalleReservaModificarEstadoDtoRequest detalleEstadoDto){
        var reservaDetalle = reservaService.modificarEstadoDetalleReserva(detalleEstadoDto, id);
        return ResponseEntity.ok(reservaDetalle);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDtoResponse> modificarDetallesReserva(
            @RequestBody ReservaDtoRequest reservaDto,
            @PathVariable Long id ){
        var reservaModificada = reservaService.modificarReserva(reservaDto,id);
        return ResponseEntity.ok(reservaModificada);
    }
}

