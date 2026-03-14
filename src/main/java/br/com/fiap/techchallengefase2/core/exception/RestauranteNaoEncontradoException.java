package br.com.fiap.techchallengefase2.core.exception;

public class RestauranteNaoEncontradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "restaurante.naoEncontrado";
    private static final String MESSAGE = "Restaurante não encontrado";
    private static final Integer HTTP_STATUS = 404;

    public RestauranteNaoEncontradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}