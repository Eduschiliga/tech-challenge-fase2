package br.com.fiap.techchallengefase2.infra.controller.model.request.usuario;

public record AtualizarUsuarioJson(
        String nome,
        String email,
        String login,
        String endereco
) {
}