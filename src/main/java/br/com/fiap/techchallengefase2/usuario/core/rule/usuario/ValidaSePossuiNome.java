package br.com.fiap.techchallengefase2.usuario.core.rule.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;

import java.util.Objects;

public class ValidaSePossuiNome implements RuleCriarUsuario {
    @Override
    public void validar(Usuario usuario) {
        if (Objects.isNull(usuario.getNome()) || usuario.getNome().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
