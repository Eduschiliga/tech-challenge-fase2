package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.AtualizarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuario;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.CriarTipoUsuarioInputDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TipoUsuarioController {
    private final CriarTipoUsuario criarTipoUsuario;
    private final AtualizarTipoUsuario atualizarTipoUsuario;
    private final BuscarTipoUsuarioPorId buscarTipoUsuarioPorId;
    private final DeletarTipoUsuario deletarTipoUsuario;
    private final BuscarTipoUsuarioPorRestaurante buscarTipoUsuarioPorRestaurante;

    public Long criar(Long usuarioLogadoId, CriarTipoUsuarioInputDTO input) {
        return criarTipoUsuario.criar(usuarioLogadoId, input.restauranteId(), input.nome());
    }

    public Long atualizar(Long usuarioLogadoId, AtualizarTipoUsuarioInputDTO input) {
        return atualizarTipoUsuario.atualizar(usuarioLogadoId, input.tipoUsuarioId(), input.nome());
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
