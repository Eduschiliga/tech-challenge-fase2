package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.dto.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.usecase.ususario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.ususario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.ususario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.ususario.deletar.DeletarUsuario;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

import static br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory.criarUsuario;

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
