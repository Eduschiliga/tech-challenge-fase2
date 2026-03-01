package br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletarRestauranteUseCase implements DeletarRestaurante {
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final RestauranteGateway restauranteGateway;

    @Override
    public void deletarPorId(Long usuarioLogadoId, Long restauranteId) {
        Restaurante restaurante = buscarRestaurantePorIdUseCase
                .buscarPorId(usuarioLogadoId, restauranteId);

        restauranteGateway.deletarPorId(restaurante.getRestauranteId());
    }
}
