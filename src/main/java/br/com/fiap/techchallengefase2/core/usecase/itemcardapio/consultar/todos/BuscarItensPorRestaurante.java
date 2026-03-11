package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos;


import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;

import java.util.List;

public interface BuscarItensPorRestaurante {
    List<ItemCardapio> buscarTodos(Long usuarioLogadoId, Long restauranteId);
}