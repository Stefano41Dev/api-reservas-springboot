package com.reservas.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ErrorNegocio extends Exception{
    private final HttpStatus  status;
    public ErrorNegocio(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.status = httpStatus;
    }
}
