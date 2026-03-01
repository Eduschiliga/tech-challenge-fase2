package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuario;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoUsuarioController {
    private final CriarTipoUsuario criarTipoUsuario;
    private final AtualizarTipoUsuario atualizarTipoUsuario;
    private final BuscarTipoUsuarioPorId buscarTipoUsuarioPorId;
    private final DeletarTipoUsuario deletarTipoUsuario;
    private final BuscarTipoUsuarioPorRestaurante buscarTipoUsuarioPorRestaurante;

    public Long criar(Long usuarioLogadoId, Long restauranteId, String nomeTipo) {
        return criarTipoUsuario.criar(usuarioLogadoId, restauranteId, nomeTipo);
    }

    public Long atualizar(Long usuarioLogadoId, Long tipoUsuarioId, String nomeTipo) {
        return atualizarTipoUsuario.atualizar(usuarioLogadoId, tipoUsuarioId, nomeTipo);
    }

    public TipoUsuario buscarPorId(Long usuarioLogadoId, Long tipoUsuarioId) {
        return buscarTipoUsuarioPorId.buscarPorId(usuarioLogadoId, tipoUsuarioId);
    }

    public List<TipoUsuario> buscarTodosPorRestaurante(Long usuarioLogadoId, Long restauranteId) {
        return buscarTipoUsuarioPorRestaurante.buscarTodosPorRestauranteId(usuarioLogadoId, restauranteId);
    }

    public void deletarTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId) {
        deletarTipoUsuario.deletarPorId(usuarioLogadoId, tipoUsuarioId);
    }
}
