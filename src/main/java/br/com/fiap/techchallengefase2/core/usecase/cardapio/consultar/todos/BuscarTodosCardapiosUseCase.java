package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTodosCardapiosUseCase implements BuscarTodosCardapios {
    private final CardapioGateway cardapioGateway;

    @Override
    public List<Cardapio> buscarTodos(Long usuarioLogadoId) {
        return cardapioGateway.buscarTodos();
    }
}
