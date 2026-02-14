package br.com.fiap.techchallengefase2.usuario.core.rule.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidaSePossuiUsuario implements RuleCriarUsuario {
    @Override
    public void validar(Usuario usuario) {
        if (Objects.isNull(usuario)) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
    }

    @Override
    public int getOrdemValidacao() {
        return 1;
    }
}
