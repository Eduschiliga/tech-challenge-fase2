package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id;


import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;

public interface BuscarCardapioPorId {
    Cardapio buscarPorId(Long cardapioId);
}