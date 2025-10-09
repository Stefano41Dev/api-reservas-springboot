package com.reservas.web.controller;

import com.reservas.application.service.HabitacionService;
import com.reservas.web.dto.habitacion.HabitacionDtoEstadoHabitacionRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoRequest;
import com.reservas.web.dto.habitacion.HabitacionDtoResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/habitacion")
public class HabitacionController {
    public final HabitacionService habitacionService;
    @PostMapping("/agregar")
    public ResponseEntity<HabitacionDtoResponse> agregarHabitacion(@RequestBody HabitacionDtoRequest habitacionDtoRequest) {
        HabitacionDtoResponse habitacionDtoResponse= habitacionService.agregarHabitacion(habitacionDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionDtoResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDtoResponse> obtenerHabitacion(@PathVariable Integer id) {
        HabitacionDtoResponse habitacionDtoResponse = habitacionService.obtenerHabitacion(id);
        return ResponseEntity.ok().body(habitacionDtoResponse);
    }
    @GetMapping
    public ResponseEntity<Page<HabitacionDtoResponse>> obtenerHabitaciones(
            @PageableDefault Pageable pageable) {
        var page = habitacionService.obtenerHabitaciones(pageable);
        return ResponseEntity.ok().body(page);
    }
    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDtoResponse> modificarHabitacion(@PathVariable Integer id, @RequestBody HabitacionDtoRequest habitacionDtoRequest) {
        var habitacion = habitacionService.modificarHabitacion(habitacionDtoRequest, id);
        return ResponseEntity.ok().body(habitacion);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HabitacionDtoResponse> modificarEstadoHabitacion(@PathVariable Integer id, @RequestBody HabitacionDtoEstadoHabitacionRequest habitacionEstadoDtoRequest) {
        var habitacion = habitacionService.modificarEstadoHabitacion(id, habitacionEstadoDtoRequest);
        return  ResponseEntity.ok().body(habitacion);
    }
}
