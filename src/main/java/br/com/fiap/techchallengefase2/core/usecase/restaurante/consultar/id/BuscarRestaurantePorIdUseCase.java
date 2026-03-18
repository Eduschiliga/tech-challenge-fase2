package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.exception.restaurante.RestauranteNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarRestaurantePorIdUseCase implements BuscarRestaurantePorId {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    private final RestauranteGateway restauranteGateway;

    @Override
    public Restaurante buscarPorId(Long usuarioLogadoId, Long restauranteId) {
        return restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(RestauranteNaoEncontradoException::new);
    }


}
