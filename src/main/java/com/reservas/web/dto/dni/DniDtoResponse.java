package com.reservas.web.dto.dni;


public record DniDtoResponse(
        boolean success,
        String message,
        DniDataResponse data
) {
}
