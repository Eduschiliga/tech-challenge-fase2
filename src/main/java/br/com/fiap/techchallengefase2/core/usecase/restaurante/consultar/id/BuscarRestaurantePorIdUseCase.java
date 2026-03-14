package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarRestaurantePorIdUseCase implements BuscarRestaurantePorId {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    private final RestauranteGateway restauranteGateway;

    @Override
    public Restaurante buscarPorId(Long usuarioLogadoId, Long restauranteId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        Restaurante restaurante = buscarPorIdOptional(restauranteId);
        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);

        validaSeUsuarioDonoRestaurante.validar(dono, restaurante.getRestauranteId());

        return restaurante;
    }

    private Restaurante buscarPorIdOptional(Long restauranteId) {
        return restauranteGateway.buscarPorId(restauranteId)
                .orElseThrow(RestauranteNaoEncontradoException::new);
    }
}
