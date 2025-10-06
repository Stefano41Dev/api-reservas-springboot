package com.reservas.application.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ErrorNegocio.class)
    public ResponseEntity<Object> handleBusinessException(ErrorNegocio ex){
        return ResponseEntity.status(ex.getStatus()).body(new ErrorDto(ex.getMessage(), LocalDateTime.now()));
    }
}
