package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id;


import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;

public interface BuscarItemCardapioPorId {
    ItemCardapio buscarPorId(Long usuarioLogadoId, Long itemCardapioId);
}