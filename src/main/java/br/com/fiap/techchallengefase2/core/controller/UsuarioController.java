package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuario;
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
    private final AtribuirTipoUsuario atribuirTipoUsuario;
    private final RemoverTipoUsuario removerTipoUsuario;

    public void atribuirTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId, Long usuarioParaAtribuirId) {
        atribuirTipoUsuario.atribuirTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioParaAtribuirId);
    }

    public void removerTipoUsuario(Long usuarioLogadoId, Long tipoUsuarioId, Long usuarioParaAtribuirId) {
        removerTipoUsuario.removerTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioParaAtribuirId);
    }

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
