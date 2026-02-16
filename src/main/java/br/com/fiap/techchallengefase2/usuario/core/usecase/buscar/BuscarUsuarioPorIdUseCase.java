package br.com.fiap.techchallengefase2.usuario.core.usecase.buscar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;

public class BuscarUsuarioPorIdUseCase implements BuscarUsuarioPorId {
    private final UsuarioGateway usuarioGateway;

    public BuscarUsuarioPorIdUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public UsuarioBase buscarPorId(Long usuarioId) {
        return usuarioGateway.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com o Id: " + usuarioId + " não encontrado."));
    }
}
