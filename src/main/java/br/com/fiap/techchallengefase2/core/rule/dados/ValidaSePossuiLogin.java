package br.com.fiap.techchallengefase2.core.rule.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.usuario.DadosUsuarioInvalidosException;

import java.util.Objects;

public class ValidaSePossuiLogin implements RuleDadosUsuario {
    @Override
    public void validar(UsuarioBase usuarioBase) {
        if (Objects.isNull(usuarioBase.getLogin()) || usuarioBase.getLogin().isEmpty() || usuarioBase.getLogin().isBlank()) {
            throw new DadosUsuarioInvalidosException("Login é obrigatório");
        }
    }
}
