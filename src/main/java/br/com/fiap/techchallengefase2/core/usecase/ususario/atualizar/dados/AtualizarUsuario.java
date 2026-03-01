package br.com.fiap.techchallengefase2.core.usecase.ususario.atualizar.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;

public interface AtualizarUsuario {
    UsuarioBase atualizar(Long usuarioLogadoId, DadosUsuarioInputDTO dadosParciaisDto);
}
