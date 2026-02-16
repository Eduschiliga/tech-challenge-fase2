package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

import java.util.Objects;

public class ValidaSePossuiUsuario implements RuleDadosUsuario {
    @Override
    public void validar(UsuarioBase usuario) {
        if (Objects.isNull(usuario)) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
    }

    @Override
    public int getOrdemValidacao() {
        return 1;
    }
}
