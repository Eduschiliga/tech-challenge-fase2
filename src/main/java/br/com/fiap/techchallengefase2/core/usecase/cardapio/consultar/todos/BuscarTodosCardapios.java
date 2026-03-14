package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;

import java.util.List;

public interface BuscarTodosCardapios {
    List<Cardapio> buscarTodos(Long usuarioLogadoId);
}
