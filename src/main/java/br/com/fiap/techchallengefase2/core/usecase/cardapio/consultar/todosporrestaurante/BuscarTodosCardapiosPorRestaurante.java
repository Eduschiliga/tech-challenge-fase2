package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;

import java.util.List;

public interface BuscarTodosCardapiosPorRestaurante {
    List<Cardapio> buscarTodos(Long usuarioLogadoId, Long restauranteId);
}
