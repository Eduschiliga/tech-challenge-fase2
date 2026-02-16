package br.com.fiap.techchallengefase2.usuario.core.usecase.criar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

public interface CriarUsuario {
    Long criar(UsuarioBase usuario);
}
