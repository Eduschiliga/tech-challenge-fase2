package br.com.fiap.techchallengefase2.core.gateway;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioGateway {

    Optional<UsuarioBase> buscarPorId(Long usuarioId);

    void deletarPorId(Long usuarioId);

    UsuarioBase salvar(UsuarioBase usuario);

    boolean existeUsuarioComLogin(String login);

    boolean existeUsuarioComEmail(String email);

    Collection<UsuarioBase> buscarTodos();

    Optional<UsuarioBase> buscarPorLogin(String login);
}
