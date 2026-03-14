package br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar;


import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;

public interface AtualizarCardapio {
    Long atualizar(Long usuarioLogadoId, AtualizarCardapioInputDTO input);
}