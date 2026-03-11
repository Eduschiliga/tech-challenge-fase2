package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletarItemCardapioUseCase implements DeletarItemCardapio {

    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final ItemCardapioGateway itemCardapioGateway;

    @Override
    public void deletarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        ItemCardapio item = buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId);
        itemCardapioGateway.deletarPorId(item.getItemCardapioId());
    }
}