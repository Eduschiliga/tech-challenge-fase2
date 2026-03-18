package br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AtualizarCardapioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        AtualizarCardapioJson atualizarCardapioJson = new AtualizarCardapioJson(
                "Cardápio Atualizado"
        );

        assertNotNull(atualizarCardapioJson);
        assertEquals("Cardápio Atualizado", atualizarCardapioJson.nome());
    }
}