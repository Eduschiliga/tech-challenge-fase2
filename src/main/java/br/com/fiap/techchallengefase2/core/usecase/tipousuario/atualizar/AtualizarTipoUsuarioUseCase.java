package br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarTipoUsuarioUseCase implements AtualizarTipoUsuario {
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final TipoUsuarioGateway tipoUsuarioGateway;

    @Override
    public Long atualizar(Long usuarioLogadoId, Long tipoUsuarioId, String nomeTipo) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        TipoUsuario tipoUsuario = buscarTipoUsuarioPorIdUseCase
                .buscarPorId(usuarioLogadoId, tipoUsuarioId);

        tipoUsuario.atualizarNome(nomeTipo);

        return tipoUsuarioGateway.salvar(tipoUsuario);
    }
}
