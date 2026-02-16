package br.com.fiap.techchallengefase2.usuario.core.gateway;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

public interface TipoUsuarioGateway {

    Long salvar(Long restauranteId, UsuarioBase novoTipoUsuario);

}
