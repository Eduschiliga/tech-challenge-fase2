package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.senha;

import br.com.fiap.techchallengefase2.usuario.core.dto.senha.AtualizarSenhaDTO;

public interface AtualizarSenhaUsuario {

    Long atualizar(Long usuarioLogadoId, AtualizarSenhaDTO atualizarSenhaDTO);

}
