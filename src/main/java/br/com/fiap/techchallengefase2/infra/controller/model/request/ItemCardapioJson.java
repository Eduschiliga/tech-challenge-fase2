package br.com.fiap.techchallengefase2.infra.controller.model.request;

public record ItemCardapioJson(
        String nome,
        String descricao,
        Double preco,
        Boolean disponivelApenasRestaurante,
        String caminhoFoto
) {
}