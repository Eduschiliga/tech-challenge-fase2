package br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtribuirTipoUsuarioUseCase implements AtribuirTipoUsuario {
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final UsuarioGateway usuarioGateway;

    @Override
    public void atribuirTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId, Long usuarioParaAtribuirId) {
        TipoUsuario tipoUsuario = buscarTipoUsuarioPorIdUseCase
                .buscarPorId(usuarioLogadoId, tipoUsuarioId);

        UsuarioBase usuarioParaAtribuir = buscarUsuarioPorIdUseCase.buscarPorId(usuarioParaAtribuirId);

        usuarioParaAtribuir.atribuirTipoUsuario(tipoUsuario);

        usuarioGateway.salvar(usuarioParaAtribuir);
    }

}
