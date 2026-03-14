package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarItemCardapioPorIdUseCase implements BuscarItemCardapioPorId {

    private final ItemCardapioGateway itemCardapioGateway;

    @Override
    public ItemCardapio buscarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        return itemCardapioGateway.buscarPorId(itemCardapioId)
                .orElseThrow(ItemCardapioNaoEncontradoException::new);
    }
}
