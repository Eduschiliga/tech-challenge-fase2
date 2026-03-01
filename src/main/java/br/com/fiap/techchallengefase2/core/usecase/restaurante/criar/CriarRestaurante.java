package br.com.fiap.techchallengefase2.core.usecase.restaurante.criar;

import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;

public interface CriarRestaurante {
    Long criar(Long usuarioLogadoId, DadosRestauranteInputDTO dadosRestauranteInputDTO);
}
