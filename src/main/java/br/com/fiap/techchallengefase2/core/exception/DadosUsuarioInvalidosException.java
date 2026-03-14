package br.com.fiap.techchallengefase2.core.exception;

import java.io.Serial;

public class DadosUsuarioInvalidosException extends SystemBaseException {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.dadosInvalidos";
    private static final Integer HTTP_STATUS = 422;

    public DadosUsuarioInvalidosException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}