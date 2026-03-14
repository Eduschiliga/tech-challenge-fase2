package br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio;

public record ItemCardapioJson(
        String nome,
        String descricao,
        Double preco,
        Boolean disponivelApenasRestaurante,
        String caminhoFoto
) {
}