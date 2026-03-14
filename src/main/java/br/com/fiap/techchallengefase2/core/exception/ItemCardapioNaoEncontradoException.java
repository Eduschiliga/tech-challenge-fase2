package br.com.fiap.techchallengefase2.core.exception;

public class ItemCardapioNaoEncontradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "itemCardapio.naoEncontrado";
    private static final String MESSAGE = "Item do cardápio não encontrado";
    private static final Integer HTTP_STATUS = 404;

    public ItemCardapioNaoEncontradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}