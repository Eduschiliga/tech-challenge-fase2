package br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.todos;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

import java.util.Collection;

public interface BuscarTodosUsuarios {

    Collection<UsuarioBase> buscarTodos();
}
