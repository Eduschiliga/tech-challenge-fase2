package br.com.fiap.techchallengefase2.usuario.core.controller;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.usuario.core.dto.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.consultar.id.BuscarUsuarioPorId;
import br.com.fiap.techchallengefase2.usuario.core.usecase.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.usuario.core.usecase.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.deletar.DeletarUsuario;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

import static br.com.fiap.techchallengefase2.usuario.core.domain.factory.UsuarioFactory.criarUsuario;

@RequiredArgsConstructor
public class UsuarioController {
    private final DeletarUsuario deletarUsuario;
    private final CriarUsuario criarUsuario;
    private final BuscarUsuarioPorId buscarUsuarioPorId;
    private final BuscarTodosUsuarios buscarTodosUsuarios;
    private final AtualizarSenhaUsuario atualizarSenhaUsuario;
    private final AtualizarUsuario atualizarUsuario;

    public void deletarUsuarioPorId(Long usuarioLogadoId, Long usuarioId) {
        deletarUsuario.deletarPorId(usuarioLogadoId, usuarioId);
    }

    public Long criar(CriarUsuarioInputDTO criarUsuarioInputDto) {
        UsuarioBase usuario = criarUsuario(
                criarUsuarioInputDto.getCategoriaUsuario(),
                criarUsuarioInputDto.getNome(),
                criarUsuarioInputDto.getEmail(),
                criarUsuarioInputDto.getLogin(),
                criarUsuarioInputDto.getSenha(),
                criarUsuarioInputDto.getEndereco()
        );

        return criarUsuario.criar(usuario).getUsuarioId();
    }

    public Collection<UsuarioBase> buscarTodosUsuarios(Long usuarioLogadoId) {
        return buscarTodosUsuarios.buscarTodos();
    }

    public UsuarioBase buscarUsuarioPorId(Long usuarioLogadoId, Long usuarioId) {
        return buscarUsuarioPorId.buscarPorId(usuarioId);
    }

    public UsuarioBase atualizarSenhaUsuario(Long usuarioLogadoId, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        return atualizarSenhaUsuario.atualizar(usuarioLogadoId, atualizarSenhaInputDto);
    }

    public UsuarioBase atualizarDadosParciaisUsuario(
            Long usuarioLogadoId,
            DadosUsuarioInputDTO dadosParciaisUsuario
    ) {
        return atualizarUsuario.atualizar(usuarioLogadoId, dadosParciaisUsuario);
    }
}
