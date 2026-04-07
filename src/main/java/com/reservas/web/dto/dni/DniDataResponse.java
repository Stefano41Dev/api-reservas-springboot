package com.reservas.web.dto.dni;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DniDataResponse (
        String nombres,
        @JsonProperty("apellido_paterno")
        String apellidoPaterno,
        @JsonProperty("apellido_materno")
        String apellidoMaterno,
        @JsonProperty("codigo_verificacion")
        Integer codigoVerificacionDni
) {}