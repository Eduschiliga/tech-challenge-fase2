package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar.AtualizarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarRestaurantePorUsuarioId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.criar.CriarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar.DeletarRestaurante;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestauranteController {
    private final CriarRestaurante criarRestaurante;
    private final AtualizarRestaurante atualizarRestaurante;
    private final BuscarRestaurantePorId buscarRestaurantePorId;
    private final DeletarRestaurante deletarRestaurante;
    private final BuscarRestaurantePorUsuarioId buscarRestaurantePorUsuarioId;

    public Long criar(Long usuarioLogadoId, DadosRestauranteInputDTO dadosRestauranteInputDTO) {
        return criarRestaurante.criar(usuarioLogadoId, dadosRestauranteInputDTO);
    }

    public Long atualizar(Long usuarioLogadoId, Long restauranteId, DadosRestauranteInputDTO dadosRestauranteInputDTO) {
        return atualizarRestaurante.atualizar(usuarioLogadoId, restauranteId, dadosRestauranteInputDTO);
    }

    public Restaurante buscarPorId(Long usuarioLogadoId, Long restauranteId) {
        return buscarRestaurantePorId.buscarPorId(usuarioLogadoId, restauranteId);
    }

    public List<Restaurante> buscarTodosPorUsuarioId(Long usuarioLogadoId) {
        return buscarRestaurantePorUsuarioId.buscarTodos(usuarioLogadoId);
    }

    public void deletarPorId(Long usuarioLogadoId, Long restauranteId) {
        deletarRestaurante.deletarPorId(usuarioLogadoId, restauranteId);
    }
}
