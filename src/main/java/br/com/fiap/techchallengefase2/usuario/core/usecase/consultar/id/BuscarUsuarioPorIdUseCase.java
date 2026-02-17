package br.com.fiap.techchallengefase2.usuario.core.usecase.consultar.id;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarUsuarioPorIdUseCase implements BuscarUsuarioPorId {
    private final UsuarioGateway usuarioGateway;

    @Override
    public UsuarioBase buscarPorId(Long usuarioId) {
        return usuarioGateway.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com o Id: " + usuarioId + " não encontrado."));
    }
}
