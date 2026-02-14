package br.com.fiap.techchallengefase2.usuario.core.rule.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidaSePossuiLogin implements RuleCriarUsuario {

    @Override
    public void validar(Usuario usuario) {
        if (Objects.isNull(usuario.getLogin()) || usuario.getLogin().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
