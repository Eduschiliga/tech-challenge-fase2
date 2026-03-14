package br.com.fiap.techchallengefase2.core.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;

import java.util.List;
import java.util.Optional;

public interface CardapioGateway {
    Long salvar(Cardapio cardapio);
    Optional<Cardapio> buscarPorId(Long id);
    List<Cardapio> buscarTodosPorRestauranteId(Long restauranteId);
    List<Cardapio> buscarTodos();
    void deletarPorId(Long id);
}