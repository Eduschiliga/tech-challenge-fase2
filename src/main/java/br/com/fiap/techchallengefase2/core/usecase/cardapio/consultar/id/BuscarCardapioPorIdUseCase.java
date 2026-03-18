package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.exception.cardapio.CardapioNaoEncontraoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarCardapioPorIdUseCase implements BuscarCardapioPorId {

    private final CardapioGateway cardapioGateway;

    @Override
    public Cardapio buscarPorId(Long cardapioId) {
        return cardapioGateway.buscarPorId(cardapioId)
                .orElseThrow(CardapioNaoEncontraoException::new);
    }
}
