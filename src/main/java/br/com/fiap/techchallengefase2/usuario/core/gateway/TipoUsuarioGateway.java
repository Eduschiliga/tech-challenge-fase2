package br.com.fiap.techchallengefase2.usuario.core.gateway;

import br.com.fiap.techchallengefase2.usuario.core.domain.tipousuario.TipoUsuarioBase;

public interface TipoUsuarioGateway {

    Long salvar(Long restauranteId, TipoUsuarioBase novoTipoUsuario);

}
