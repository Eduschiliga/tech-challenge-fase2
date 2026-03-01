package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;

import java.util.List;

public interface BuscarRestaurantePorUsuarioId {

    List<Restaurante> buscarTodos(Long usuarioLogadoId);

}
