package br.com.fiap.techchallengefase2.usuario.core.usecase.criar;

import br.com.fiap.techchallengefase2.usuario.core.dto.CriarUsuarioDTO;

public interface CriarUsuario {
    Long criar(CriarUsuarioDTO criarUsuarioDto);
}
