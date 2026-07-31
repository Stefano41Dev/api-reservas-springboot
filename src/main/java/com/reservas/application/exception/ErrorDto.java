package com.reservas.application.exception;

import java.time.LocalDateTime;

public record ErrorDto (
        String mensaje,
        LocalDateTime fecha
){
}
