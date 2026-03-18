package br.com.fiap.techchallengefase2.core.exception.usuario;

import br.com.fiap.techchallengefase2.core.exception.SystemBaseException;

public class CategoriaNaoEncontradaException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "categoriaUsuario.naoEncontrado";
    private static final String MESSAGE = "Categoria de usuário não encontrada";
    private static final Integer HTTP_STATUS = 404;

    public CategoriaNaoEncontradaException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}