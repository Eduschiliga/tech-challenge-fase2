package br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TipoUsuarioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        TipoUsuarioJson tipoUsuarioJson = new TipoUsuarioJson(
                "Gerente",
                10L
        );

        assertNotNull(tipoUsuarioJson);
        assertEquals("Gerente", tipoUsuarioJson.nome());
        assertEquals(10L, tipoUsuarioJson.restauranteId());
    }
}