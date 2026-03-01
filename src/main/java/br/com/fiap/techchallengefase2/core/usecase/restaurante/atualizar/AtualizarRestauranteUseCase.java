package br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar;

import br.com.fiap.techchallengefase2.core.domain.factory.RestauranteFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarRestauranteUseCase implements AtualizarRestaurante {
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
    private final RestauranteGateway restauranteGateway;

    @Override
    public Long atualizar(Long usuarioLogadoId, Long restauranteId, DadosRestauranteInputDTO dados) {
        Restaurante restaurante = buscarRestaurantePorIdUseCase
                .buscarPorId(usuarioLogadoId, restauranteId);

        Restaurante restauranteAtualizado = RestauranteFactory.atualizar(restaurante, dados);

        return restauranteGateway.salvar(restauranteAtualizado);
    }
}
