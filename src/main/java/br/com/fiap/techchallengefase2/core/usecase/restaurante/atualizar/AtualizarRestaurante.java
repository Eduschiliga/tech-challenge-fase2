package br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar;

import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;

public interface AtualizarRestaurante {

    Long atualizar(Long usuarioLogadoId, Long restauranteId, DadosRestauranteInputDTO dadosUsuarioInputDTO);

}
