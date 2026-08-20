package com.reservas;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HabitacionTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void agregarHabitacionDebeRetornarStatusOk() throws Exception{

        MvcResult loginResult = mockMvc.perform(
                        post("/auth/login")
                                .content("""
                            {
                              "email": "stefano@gmail.com",
                              "password": "Stefano"
                            }
                            """)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String token =  JsonPath
                .read(
                        loginResult.getResponse().getContentAsString(),
                        "$.token"
                );

        mockMvc.perform(post("/habitacion")
                        .header("Authorization", "Bearer " + token)
                        .content("{\n" +
                        "  \"urlImagePrincipal\": \"string\",\n" +
                        "  \"listaImagenes\": [\n" +
                        "    {\n" +
                        "      \"url\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"numeroHabitacion\": 1992,\n" +
                        "  \"cantidadCamas\": 1,\n" +
                        "  \"numeroPiso\": 3,\n" +
                        "  \"tipoHabitacion\": \"SUITE\",\n" +
                        "  \"tarifaDiaria\": 20,\n" +
                        "  \"descripcion\": \"string\",\n" +
                        "  \"capacidad\": 2\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());
    }
}
