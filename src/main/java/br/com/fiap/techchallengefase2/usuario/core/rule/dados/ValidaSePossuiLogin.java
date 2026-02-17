package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

import java.util.Objects;

public class ValidaSePossuiLogin implements RuleDadosUsuario {
    @Override
    public void validar(UsuarioBase usuarioBase) {
        if (Objects.isNull(usuarioBase.getLogin()) || usuarioBase.getLogin().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
