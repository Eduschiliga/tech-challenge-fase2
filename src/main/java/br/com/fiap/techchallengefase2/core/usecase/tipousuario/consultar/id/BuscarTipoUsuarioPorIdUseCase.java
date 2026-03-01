package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarTipoUsuarioPorIdUseCase implements BuscarTipoUsuarioPorId {
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public TipoUsuario buscarPorId(Long usuarioLogadoId, Long tipoUsuarioId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        TipoUsuario tipoUsuario = buscarPorIdOptional(tipoUsuarioId);
        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);

        validaSeUsuarioDonoRestaurante.validar(dono, tipoUsuario.getRestauranteId());

        return tipoUsuario;
    }


    private TipoUsuario buscarPorIdOptional(Long tipoUsuarioId) {
        return tipoUsuarioGateway.buscarPorId(tipoUsuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Usuário do Id #" + tipoUsuarioId + " não encontrado"));
    }

}
