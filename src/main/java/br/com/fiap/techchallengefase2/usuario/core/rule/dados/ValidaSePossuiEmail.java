package br.com.fiap.techchallengefase2.usuario.core.rule.dados;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidaSePossuiEmail implements RuleDadosUsuario {
    // Regex para validar e-mail (RFC 5322 simplificado)
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public void validar(UsuarioBase usuario) {
        if (Objects.isNull(usuario.getEmail()) || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("E-mail não pode ser nulo ou vazio");
        }

        if (!EMAIL_PATTERN.matcher(usuario.getEmail()).matches()) {
            throw new IllegalArgumentException("E-mail inválido: " + usuario.getEmail());
        }
    }
}
