package br.com.fiap.techchallengefase2.usuario.core.gateway;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioGateway {

    Optional<UsuarioBase> buscarPorId(Long usuarioId);

    void deletarPorId(Long usuarioId);

    Long salvar(UsuarioBase usuario);

    Long atualizarSenha(String senhaCodificada, Long usuarioId);

    Long atualizarCategoria(Long usuarioId, Integer categoriaUsuario);

    boolean existeUsuarioComLogin(String login);

    boolean existeUsuarioComEmail(String email);

    Collection<UsuarioBase> buscarTodos();
}
