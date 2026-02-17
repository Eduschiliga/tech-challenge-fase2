package br.com.fiap.techchallengefase2.usuario.core.usecase.consultar.todos;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class BuscarTodosUsuariosUseCase implements BuscarTodosUsuarios {
    private final UsuarioGateway usuarioGateway;

    @Override
    public Collection<UsuarioBase> buscarTodos() {
        return usuarioGateway.buscarTodos();
    }
}
