package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BuscarTipoUsuarioPorRestautanteUseCase implements BuscarTipoUsuarioPorRestaurante {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public List<TipoUsuario> buscarTodosPorRestauranteId(Long usuarioLogadoId, Long restauranteId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);
        validaSeUsuarioDonoRestaurante.validar(dono, restauranteId);

        return tipoUsuarioGateway.buscarTodosPorRestauranteId(restauranteId);
    }
}
