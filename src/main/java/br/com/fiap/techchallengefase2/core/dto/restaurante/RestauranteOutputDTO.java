package br.com.fiap.techchallengefase2.core.dto.restaurante;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;

public record RestauranteOutputDTO(
        Long restauranteId,
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        Long donoId
) {
    public static RestauranteOutputDTO fromDomain(Restaurante restaurante) {
        return new RestauranteOutputDTO(
                restaurante.getRestauranteId(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getUsuarioId()
        );
    }
}