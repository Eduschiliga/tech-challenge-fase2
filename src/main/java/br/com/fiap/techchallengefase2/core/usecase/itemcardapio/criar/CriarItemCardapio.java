package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar;

import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;

public interface CriarItemCardapio {
    Long criar(Long usuarioLogadoId, Long restauranteId, DadosItemCardapioInputDTO dados);
}
