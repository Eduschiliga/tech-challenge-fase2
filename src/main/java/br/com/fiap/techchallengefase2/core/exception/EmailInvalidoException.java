package br.com.fiap.techchallengefase2.core.exception;


public class EmailInvalidoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.emailInvalido";
    private static final String DEFAULT_MESSAGE = "Formato de e-mail inválido.";
    private static final Integer HTTP_STATUS = 422;

    public EmailInvalidoException() {
        super(CODE, DEFAULT_MESSAGE, HTTP_STATUS);
    }

    public EmailInvalidoException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}