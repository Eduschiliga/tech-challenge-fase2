package br.com.fiap.techchallengefase2.core.exception;

public class SenhaAtualIncorretaException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.senhaAtualIncorreta";
    private static final String MESSAGE = "Senha atual não confere";
    private static final Integer HTTP_STATUS = 422;

    public SenhaAtualIncorretaException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}