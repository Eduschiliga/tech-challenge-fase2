package br.com.fiap.techchallengefase2.core.exception;

public class EmailJaCadastradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.emailJaCadastrado";
    private static final String MESSAGE = "O e-mail informado já está em uso";
    private static final Integer HTTP_STATUS = 422;

    public EmailJaCadastradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}