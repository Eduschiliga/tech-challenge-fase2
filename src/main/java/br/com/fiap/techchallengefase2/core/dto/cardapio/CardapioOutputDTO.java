package br.com.fiap.techchallengefase2.core.dto.cardapio;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.dto.restaurante.RestauranteOutputDTO;

import java.util.List;
import java.util.stream.Collectors;

public record CardapioOutputDTO(
        Long cardapioId,
        RestauranteOutputDTO restaurante,
        List<ItemCardapioOutputDTO> itens,
        String nome
) {
    public static CardapioOutputDTO fromDomain(Cardapio cardapio) {
        return new CardapioOutputDTO(
                cardapio.getCardapioId(),
                cardapio.getRestaurante() != null ? RestauranteOutputDTO.fromDomain(cardapio.getRestaurante()) : null,
                cardapio.getItens() != null ? cardapio.getItens().stream().map(ItemCardapioOutputDTO::fromDomain).collect(Collectors.toList()) : null,
                cardapio.getNome()
        );
    }
}
