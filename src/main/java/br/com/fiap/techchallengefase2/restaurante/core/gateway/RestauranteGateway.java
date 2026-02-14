package br.com.fiap.techchallengefase2.restaurante.core.gateway;

import br.com.fiap.techchallengefase2.usuario.core.domain.restaurante.Restaurante;

import java.util.List;

public interface RestauranteGateway {
    List<Restaurante> obterPorUserId(Long usuarioId);
}
