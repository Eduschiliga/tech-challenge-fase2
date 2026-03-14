package br.com.fiap.techchallengefase2.core.usecase.cardapio.criar;

import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;

public interface CriarCardapio {
    Long criar(Long usuarioLogadoId, CriarCardapioInputDTO input);
}
