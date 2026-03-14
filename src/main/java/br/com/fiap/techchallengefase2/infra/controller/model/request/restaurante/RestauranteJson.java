package br.com.fiap.techchallengefase2.infra.controller.model.request.restaurante;

public record RestauranteJson(
        String nome,
        String endereco,
        String tipoCozinha,
        String horarioFuncionamento
) {
}