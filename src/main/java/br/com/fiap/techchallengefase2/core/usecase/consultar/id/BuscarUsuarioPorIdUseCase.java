package br.com.fiap.techchallengefase2.core.usecase.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
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
