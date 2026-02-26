package br.com.fiap.techchallengefase2.core.gateway;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;

public interface TipoUsuarioGateway {

    Long salvar(Long restauranteId, UsuarioBase novoTipoUsuario);

}
