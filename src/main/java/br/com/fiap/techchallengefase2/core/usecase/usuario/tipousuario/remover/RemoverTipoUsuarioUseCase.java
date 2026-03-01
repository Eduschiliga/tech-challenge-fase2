package br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoverTipoUsuarioUseCase implements RemoverTipoUsuario {
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final UsuarioGateway usuarioGateway;

    @Override
    public void removerTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId, Long usuarioAlvoId) {
        TipoUsuario tipoUsuario = buscarTipoUsuarioPorIdUseCase
                .buscarPorId(usuarioLogadoId, tipoUsuarioId);

        UsuarioBase usuarioAlvo = buscarUsuarioPorIdUseCase.buscarPorId(usuarioAlvoId);

        usuarioAlvo.removerTipoUsuario(tipoUsuario);

        usuarioGateway.salvar(usuarioAlvo);
    }
}