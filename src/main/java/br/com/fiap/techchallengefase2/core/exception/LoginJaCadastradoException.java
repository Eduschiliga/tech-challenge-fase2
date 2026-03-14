package br.com.fiap.techchallengefase2.core.exception;

public class LoginJaCadastradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuario.loginJaCadastrado";
    private static final String MESSAGE = "O login informado já está em uso";
    private static final Integer HTTP_STATUS = 422;

    public LoginJaCadastradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}