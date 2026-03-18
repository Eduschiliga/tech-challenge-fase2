package br.com.fiap.techchallengefase2.infra.controller.model.request.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AtualizarUsuarioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        AtualizarUsuarioJson atualizarUsuarioJson = new AtualizarUsuarioJson(
                "Maria Oliveira",
                "maria.oliveira@teste.com",
                "mariao",
                "Avenida Paulista, 1000"
        );

        assertNotNull(atualizarUsuarioJson);
        assertEquals("Maria Oliveira", atualizarUsuarioJson.nome());
        assertEquals("maria.oliveira@teste.com", atualizarUsuarioJson.email());
        assertEquals("mariao", atualizarUsuarioJson.login());
        assertEquals("Avenida Paulista, 1000", atualizarUsuarioJson.endereco());
    }
}