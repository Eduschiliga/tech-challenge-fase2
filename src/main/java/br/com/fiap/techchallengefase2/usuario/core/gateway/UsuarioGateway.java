package br.com.fiap.techchallengefase2.usuario.core.gateway;

import br.com.fiap.techchallengefase2.usuario.core.domain.tipousuario.TipoUsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;

import java.util.Optional;

public interface UsuarioGateway {

    Optional<TipoUsuarioBase> buscarPorId(Long usuarioBaseId);

    Long salvar(Usuario usuario);

}
