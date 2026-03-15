package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTodosRestaurantesUseCase implements BuscarTodosRestaurantes {
    private final RestauranteGateway restauranteGateway;

    @Override
    public List<Restaurante> buscarTodos() {
        return restauranteGateway.buscarTodos();
    }
}
