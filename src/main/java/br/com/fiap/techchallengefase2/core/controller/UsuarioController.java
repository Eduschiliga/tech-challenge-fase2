package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.DesvincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.VincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.UsuarioOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuario;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory.criarUsuario;


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

    public void atribuirTipoUsuario(Long usuarioLogadoId, VincularUsuarioInputDTO input) {
        atribuirTipoUsuario.atribuirTipoUsuario(
                usuarioLogadoId,
                input.tipoUsuarioId(),
                input.usuarioParaAtribuirId()
        );
    }

    public void removerTipoUsuario(Long usuarioLogadoId, DesvincularUsuarioInputDTO input) {
        removerTipoUsuario.removerTipoUsuario(
                usuarioLogadoId,
                input.tipoUsuarioId(),
                input.usuarioParaAtribuirId()
        );
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

    public List<UsuarioOutputDTO> buscarTodosUsuarios(Long usuarioLogadoId) {
        return buscarTodosUsuarios.buscarTodos().stream()
                .map(UsuarioOutputDTO::fromDomain)
                .toList();
    }

    public UsuarioOutputDTO buscarUsuarioPorId(Long usuarioLogadoId, Long usuarioId) {
        UsuarioBase usuario = buscarUsuarioPorId.buscarPorId(usuarioId);
        return UsuarioOutputDTO.fromDomain(usuario);
    }

    public UsuarioOutputDTO atualizarSenhaUsuario(Long usuarioLogadoId, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        UsuarioBase usuario = atualizarSenhaUsuario.atualizar(usuarioLogadoId, atualizarSenhaInputDto);
        return UsuarioOutputDTO.fromDomain(usuario);
    }

    public UsuarioOutputDTO atualizarDadosParciaisUsuario(
            Long usuarioLogadoId,
            DadosUsuarioInputDTO dadosParciaisUsuario
    ) {
        UsuarioBase usuario = atualizarUsuario.atualizar(usuarioLogadoId, dadosParciaisUsuario);
        return UsuarioOutputDTO.fromDomain(usuario);
    }
}
