package br.com.fiap.techchallengefase2.core.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;

import java.util.List;
import java.util.Optional;

public interface ItemCardapioGateway {

    Long salvar(ItemCardapio itemCardapio);

    Optional<ItemCardapio> buscarPorId(Long itemCardapioId);

    List<ItemCardapio> buscarTodosPorCardapioId(Long cardapioId);

    void deletarPorId(Long itemCardapioId);
}