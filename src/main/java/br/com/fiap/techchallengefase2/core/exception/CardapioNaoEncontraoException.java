package br.com.fiap.techchallengefase2.core.exception;

public class CardapioNaoEncontraoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "cardapio.naoEncontrado";
    private static final String MESSAGE = "Cardapio não encontrado";
    private static final Integer HTTP_STATUS = 404;

    public CardapioNaoEncontraoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}