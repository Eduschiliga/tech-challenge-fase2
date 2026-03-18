package br.com.fiap.techchallengefase2.infra.gateway.exception.cardapio;

import br.com.fiap.techchallengefase2.infra.gateway.exception.usuario.SystemBaseException;

public class CardapioNaoEncontradoException extends SystemBaseException {
    private static final long serialVersionUID = 1L;

    private static final String CODE = "cardapio.naoEncontrado";
    private static final String MESSAGE = "Cardápio não encontrado";
    private static final Integer HTTP_STATUS = 404;

    public CardapioNaoEncontradoException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}