package br.com.fiap.techchallengefase2.usuario.core.usecase.deletar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.id.BuscarUsuarioPorIdUseCase;

import java.util.Objects;

public class DeletarUsuarioUseCase implements DeletarUsuario {

    private final UsuarioGateway usuarioGateway;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    public DeletarUsuarioUseCase(
            UsuarioGateway usuarioGateway,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase
    ) {
        this.usuarioGateway = usuarioGateway;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
    }

    @Override
    public void deletarPorId(Long usuarioLogadoId, Long usuarioId) {
        UsuarioBase usuario = buscarUsuarioPorIdUseCase.buscarPorId(usuarioId);

        if (Objects.equals(usuario.getUsuarioId(), usuarioId)) {
            throw new IllegalArgumentException("Não é possível deletar o registro de outros usuários");
        }

        usuarioGateway.deletarPorId(usuarioId);
    }
}
