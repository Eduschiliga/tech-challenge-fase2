package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

import java.util.Objects;

public class ValidaSePossuiSenha  implements RuleDadosUsuario {
    @Override
    public void validar(UsuarioBase usuario) {
        if (Objects.isNull(usuario.getSenha()) || usuario.getSenha().isEmpty() || usuario.getSenha().length() < 8) {
            throw new IllegalArgumentException();
        }
    }
}
