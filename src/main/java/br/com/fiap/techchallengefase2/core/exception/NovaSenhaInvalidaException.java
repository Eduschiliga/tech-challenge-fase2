package br.com.fiap.techchallengefase2.core.exception;

public class NovaSenhaInvalidaException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.novaSenhaInvalida";
    private static final String MESSAGE = "Nova senha não pode ser vazia ou menor que 8 caracteres";
    private static final Integer HTTP_STATUS = 422;

    public NovaSenhaInvalidaException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}