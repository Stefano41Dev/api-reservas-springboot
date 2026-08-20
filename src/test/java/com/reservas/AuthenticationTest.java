package com.reservas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void cuandoIniciaSesionRecibeUnStatusOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .content("{\n" +
                        "  \"email\": \"stefano@gmail.com\",\n" +
                        "  \"password\": \"Stefano\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.refreshToken").exists());

    }
}
