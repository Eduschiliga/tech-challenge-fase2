package br.com.fiap.techchallengefase2.core.rule.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.EmailInvalidoException;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidaSePossuiEmail implements RuleDadosUsuario {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public void validar(UsuarioBase usuarioBase) {
        if (Objects.isNull(usuarioBase.getEmail()) || usuarioBase.getEmail().trim().isEmpty()) {
            throw new EmailInvalidoException("E-mail é obrigatório e não pode estar vazio.");
        }

        if (!EMAIL_PATTERN.matcher(usuarioBase.getEmail()).matches()) {
            throw new EmailInvalidoException("O formato do e-mail informado é inválido.");
        }
    }
}