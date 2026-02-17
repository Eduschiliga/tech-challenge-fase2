package br.com.fiap.techchallengefase2.usuario.core.usecase.consultar.id;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

public interface BuscarUsuarioPorId {
    UsuarioBase buscarPorId(Long usuarioId);
}
