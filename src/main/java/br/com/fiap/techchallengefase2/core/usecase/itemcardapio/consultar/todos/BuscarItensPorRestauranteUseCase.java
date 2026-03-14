package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarItensPorRestauranteUseCase implements BuscarItensPorRestaurante {
    private final ItemCardapioGateway itemCardapioGateway;

    @Override
    public List<ItemCardapio> buscarTodos(Long usuarioLogadoId, Long cardapioId) {
        return itemCardapioGateway.buscarTodosPorCardapioId(cardapioId);
    }
}
