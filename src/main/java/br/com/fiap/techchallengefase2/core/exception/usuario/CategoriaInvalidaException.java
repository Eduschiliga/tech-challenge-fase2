package br.com.fiap.techchallengefase2.core.exception.usuario;

import br.com.fiap.techchallengefase2.core.exception.SystemBaseException;

public class CategoriaInvalidaException extends SystemBaseException {
    private static final long serialVersionUID = -6678327325055715089L;

    private static final String CODE = "usuario.tipoInvalido";
    private static final String MESSAGE = "Tipo de usuário inválido";
    private static final Integer HTTP_STATUS = 422;

    public CategoriaInvalidaException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}