package br.com.fiap.techchallengefase2.core.usecase.usuario.deletar;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;

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

        if (!Objects.equals(usuario.getUsuarioId(), usuarioLogadoId)) {
            throw new IllegalArgumentException("Não é possível deletar o registro de outros usuários");
        }

        usuarioGateway.deletarPorId(usuarioId);
    }
}
