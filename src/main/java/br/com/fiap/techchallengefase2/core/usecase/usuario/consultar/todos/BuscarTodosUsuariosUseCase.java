package br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
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
