package br.com.fiap.techchallengefase2.core.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteGateway {

    Long salvar(Restaurante restaurante);

    Optional<Restaurante> buscarPorId(Long restauranteId);

    List<Restaurante> buscarTodosPorUsuarioId(Long usuarioLogadoId);

    void deletarPorId(Long restauranteId);
}
