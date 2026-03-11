package br.com.fiap.techchallengefase2.core.dto.itemcardapio;

public record DadosItemCardapioInputDTO(
        String nome,
        String descricao,
        Double preco,
        Boolean disponivelApenasRestaurante,
        String caminhoFoto
) {
}
