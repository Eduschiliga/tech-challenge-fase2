package br.com.fiap.techchallengefase2.core.dto.itemcardapio;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;

public record ItemCardapioOutputDTO(
        Long itemCardapioId,
        String nome,
        String descricao,
        Double preco,
        Boolean disponivelApenasRestaurante,
        String caminhoFoto,
        Long restauranteId
) {

    public static ItemCardapioOutputDTO fromDomain(ItemCardapio itemCardapio) {
        return new ItemCardapioOutputDTO(
                itemCardapio.getItemCardapioId(),
                itemCardapio.getNome(),
                itemCardapio.getDescricao(),
                itemCardapio.getPreco(),
                itemCardapio.getDisponivelApenasRestaurante(),
                itemCardapio.getCaminhoFoto(),
                itemCardapio.getRestauranteId()
        );
    }
}
