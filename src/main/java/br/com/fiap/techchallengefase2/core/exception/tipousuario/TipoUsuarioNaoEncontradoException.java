package br.com.fiap.techchallengefase2.core.exception.tipousuario;

import br.com.fiap.techchallengefase2.core.exception.SystemBaseException;

public class TipoUsuarioNaoEncontradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "tipoUsuario.naoEncontrado";
    private static final String MESSAGE = "Tipo de usuário não encontrado";
    private static final Integer HTTP_STATUS = 404;

    public TipoUsuarioNaoEncontradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}