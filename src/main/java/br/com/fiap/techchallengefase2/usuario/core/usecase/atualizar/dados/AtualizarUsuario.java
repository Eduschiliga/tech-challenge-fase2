package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosUsuarioInputDTO;

public interface AtualizarUsuario {
    UsuarioBase atualizar(Long usuarioLogadoId, DadosUsuarioInputDTO dadosParciaisDto);
}
