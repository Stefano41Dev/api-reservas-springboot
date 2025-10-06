package com.reservas.application.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorDto (
        String mensaje,
        LocalDateTime fecha
){
}
