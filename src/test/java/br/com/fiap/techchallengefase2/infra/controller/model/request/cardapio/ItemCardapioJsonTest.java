package br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemCardapioJsonTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        ItemCardapioJson itemCardapioJson = new ItemCardapioJson(
                "X-Burger",
                "Pão, carne e queijo",
                25.50,
                true,
                "/imagens/xburger.png"
        );

        assertNotNull(itemCardapioJson);
        assertEquals("X-Burger", itemCardapioJson.nome());
        assertEquals("Pão, carne e queijo", itemCardapioJson.descricao());
        assertEquals(25.50, itemCardapioJson.preco());
        assertTrue(itemCardapioJson.disponivelApenasRestaurante());
        assertEquals("/imagens/xburger.png", itemCardapioJson.caminhoFoto());
    }
}