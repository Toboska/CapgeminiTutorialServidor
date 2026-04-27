package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrestamoIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl() {
        return "http://localhost:" + port + "/prestamo";
    }

    @Test
    void deberiaCrearPrestamoCorrectamente() {
        PrestamoDto dto = new PrestamoDto();

        dto.setFechaPrestamo(LocalDate.now());
        dto.setFechaDevolucion(LocalDate.now().plusDays(7));

        // IMPORTANTE: IDs deben existir en BD
        dto.setGame(new com.ccsw.tutorial.game.model.GameDto());
        dto.getGame().setId(1L);

        dto.setClient(new com.ccsw.tutorial.client.model.ClientDto());
        dto.getClient().setId(1L);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PrestamoDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                getUrl(),
                HttpMethod.PUT,
                request,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void noDeberiaCrearPrestamoConFechasInvalidas() {
        PrestamoDto dto = new PrestamoDto();

        dto.setFechaPrestamo(LocalDate.now());
        dto.setFechaDevolucion(LocalDate.now().minusDays(1)); // inválido

        dto.setGame(new com.ccsw.tutorial.game.model.GameDto());
        dto.getGame().setId(1L);

        dto.setClient(new com.ccsw.tutorial.client.model.ClientDto());
        dto.getClient().setId(1L);

        HttpEntity<PrestamoDto> request = new HttpEntity<>(dto);

        ResponseEntity<String> response = restTemplate.exchange(
                getUrl(),
                HttpMethod.PUT,
                request,
                String.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deberiaObtenerListadoPrestamos() {
        ResponseEntity<String> response = restTemplate.exchange(
                getUrl(),
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deberiaBorrarPrestamo() {
        Long id = 1L; // debe existir

        ResponseEntity<Void> response = restTemplate.exchange(
                getUrl() + "/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}