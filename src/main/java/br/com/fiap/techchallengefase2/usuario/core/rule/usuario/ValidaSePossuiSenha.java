package br.com.fiap.techchallengefase2.usuario.core.rule.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;

import java.util.Objects;

public class ValidaSePossuiSenha  implements RuleCriarUsuario{
    @Override
    public void validar(Usuario usuario) {
        if (Objects.isNull(usuario.getSenha()) || usuario.getSenha().isEmpty() || usuario.getSenha().length() < 8) {
            throw new IllegalArgumentException();
        }
    }
}
