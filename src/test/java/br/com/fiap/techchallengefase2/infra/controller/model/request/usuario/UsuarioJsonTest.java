package br.com.fiap.techchallengefase2.infra.controller.model.request.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioJsonTest {

    @Test
    void deveInstanciarAcessarPropriedadesCorretamente() {
        UsuarioJson usuarioJson = new UsuarioJson(
                "João Silva",
                "Rua Teste, 123",
                "joao@teste.com",
                "joaosilva",
                "senha123",
                1
        );

        assertNotNull(usuarioJson);
        assertEquals("João Silva", usuarioJson.nome());
        assertEquals("Rua Teste, 123", usuarioJson.endereco());
        assertEquals("joao@teste.com", usuarioJson.email());
        assertEquals("joaosilva", usuarioJson.login());
        assertEquals("senha123", usuarioJson.senha());
        assertEquals(1, usuarioJson.categoria());
    }
}