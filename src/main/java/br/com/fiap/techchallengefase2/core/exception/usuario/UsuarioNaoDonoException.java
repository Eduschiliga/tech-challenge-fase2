package br.com.fiap.techchallengefase2.core.exception.usuario;

import br.com.fiap.techchallengefase2.core.exception.SystemBaseException;

public class UsuarioNaoDonoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.naoDono";
    private static final String MESSAGE = "Usuário não é dono do restaurante";
    private static final Integer HTTP_STATUS = 403;

    public UsuarioNaoDonoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}