package br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.todos;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;

import java.util.Collection;

public class BuscarTodosUsuariosUseCase implements BuscarTodosUsuarios {
    private final UsuarioGateway usuarioGateway;

    public BuscarTodosUsuariosUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Collection<UsuarioBase> buscarTodos() {
        return usuarioGateway.buscarTodos();
    }
}
