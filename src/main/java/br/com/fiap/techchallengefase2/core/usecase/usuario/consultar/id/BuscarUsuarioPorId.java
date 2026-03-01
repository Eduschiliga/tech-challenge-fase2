package br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;

public interface BuscarUsuarioPorId {
    UsuarioBase buscarPorId(Long usuarioId);
}
