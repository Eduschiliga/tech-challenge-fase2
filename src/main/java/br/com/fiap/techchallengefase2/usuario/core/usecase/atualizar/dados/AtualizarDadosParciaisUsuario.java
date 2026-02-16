package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

public interface AtualizarDadosParciaisUsuario {
    Long atualizar(Long usuarioLogadoId, UsuarioBase usuario);
}
