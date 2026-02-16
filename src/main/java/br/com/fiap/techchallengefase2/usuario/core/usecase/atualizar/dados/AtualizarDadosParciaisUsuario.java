package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;

public interface AtualizarDadosParciaisUsuario {
    Long atualizar(Long usuarioLogadoId, DadosParciaisUsuarioDTO dadosParciaisDto);
}
