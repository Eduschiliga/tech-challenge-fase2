package br.com.fiap.techchallengefase2.infra.controller.model.request;

public record TipoUsuarioJson(
        String nome,
        Long restauranteId
) {
}