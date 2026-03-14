package br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario;

public record TipoUsuarioJson(
        String nome,
        Long restauranteId
) {
}