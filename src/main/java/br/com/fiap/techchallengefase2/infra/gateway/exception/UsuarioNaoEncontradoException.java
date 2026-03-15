package br.com.fiap.techchallengefase2.infra.gateway.exception;

import br.com.fiap.techchallengefase2.core.exception.SystemBaseException;

public class UsuarioNaoEncontradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "usuarioGateway.naoEncontrado";
    private static final String MESSAGE = "Usuário não encontrado";
    private static final Integer HTTP_STATUS = 404;

    public UsuarioNaoEncontradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}