package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar;


import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;

public interface AtualizarItemCardapio {
    Long atualizar(Long usuarioLogadoId, Long itemCardapioId, DadosItemCardapioInputDTO dados);
}