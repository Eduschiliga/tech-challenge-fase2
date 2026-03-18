package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.idusuario;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarRestaurantePorUsuarioIdUseCase implements BuscarRestaurantePorUsuarioId {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;

    private final RestauranteGateway restauranteGateway;

    @Override
    public List<Restaurante> buscarTodos(Long usuarioLogadoId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        return restauranteGateway.buscarTodosPorUsuarioId(usuarioLogadoId);
    }
}
