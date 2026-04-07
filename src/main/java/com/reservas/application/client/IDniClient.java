package com.reservas.application.client;

import com.reservas.web.dto.dni.DniDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "dniClient", url = "https://api.json.pe")
public interface IDniClient {
    @PostMapping("/api/dni")
    DniDtoResponse consultarDni(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body
    );
}
