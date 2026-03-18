package br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardapioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        CardapioJson cardapioJson = new CardapioJson(
                "Cardápio de Sobremesas"
        );

        assertNotNull(cardapioJson);
        assertEquals("Cardápio de Sobremesas", cardapioJson.nome());
    }
}