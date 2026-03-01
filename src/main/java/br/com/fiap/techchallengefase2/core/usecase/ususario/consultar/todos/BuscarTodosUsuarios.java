package br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;

import java.util.Collection;

public interface BuscarTodosUsuarios {

    Collection<UsuarioBase> buscarTodos();
}
