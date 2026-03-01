package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;

public interface BuscarRestaurantePorId {

    Restaurante buscarPorId(Long usuarioLogadoId, Long restauranteId);

}
