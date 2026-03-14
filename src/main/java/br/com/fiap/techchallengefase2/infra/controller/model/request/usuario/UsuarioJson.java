package br.com.fiap.techchallengefase2.infra.controller.model.request.usuario;

public record UsuarioJson(
        String nome,
        String endereco,
        String email,
        String login,
        String senha,
        Integer categoria
) {
}