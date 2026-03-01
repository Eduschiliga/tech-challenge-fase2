package br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletarTipoUsuarioUseCase implements DeletarTipoUsuario {
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public void deletarPorId(Long usuarioLogadoId, Long tipoUsuarioId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        TipoUsuario tipoUsuario = buscarTipoUsuarioPorIdUseCase
                .buscarPorId(usuarioLogadoId, tipoUsuarioId);

        tipoUsuarioGateway.deletarPorId(tipoUsuario.getTipoUsuarioId());
    }
}
