package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTodosCardapiosUseCase implements BuscarTodosCardapiosPorRestaurante {
    private final CardapioGateway cardapioGateway;

    @Override
    public List<Cardapio> buscarTodos(Long usuarioLogadoId, Long restauranteId) {
        return cardapioGateway.buscarTodosPorRestauranteId(restauranteId);
    }
}
