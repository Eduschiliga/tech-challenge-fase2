package br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CriarCardapioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        CriarCardapioJson criarCardapioJson = new CriarCardapioJson(
                "Cardápio Principal"
        );

        assertNotNull(criarCardapioJson);
        assertEquals("Cardápio Principal", criarCardapioJson.nome());
    }
}