package br.com.fiap.techchallengefase2.infra.controller.model.request.restaurante;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestauranteJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        RestauranteJson restauranteJson = new RestauranteJson(
                "Restaurante Saboroso",
                "Rua das Flores, 123",
                "Brasileira",
                "08:00 - 22:00"
        );

        assertNotNull(restauranteJson);
        assertEquals("Restaurante Saboroso", restauranteJson.nome());
        assertEquals("Rua das Flores, 123", restauranteJson.endereco());
        assertEquals("Brasileira", restauranteJson.tipoCozinha());
        assertEquals("08:00 - 22:00", restauranteJson.horarioFuncionamento());
    }
}