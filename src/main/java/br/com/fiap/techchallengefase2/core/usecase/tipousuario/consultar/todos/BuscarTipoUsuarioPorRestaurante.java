package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;

import java.util.List;

public interface BuscarTipoUsuarioPorRestaurante {

    List<TipoUsuario> buscarTodosPorRestauranteId(Long usuarioLogadoId, Long restauranteId);

}
