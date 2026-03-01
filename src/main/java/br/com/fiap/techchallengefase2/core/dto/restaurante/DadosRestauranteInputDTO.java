package br.com.fiap.techchallengefase2.core.dto.restaurante;

public record DadosRestauranteInputDTO(
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento
) {
}
