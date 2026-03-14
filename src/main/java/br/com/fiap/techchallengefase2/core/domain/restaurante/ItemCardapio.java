package br.com.fiap.techchallengefase2.core.domain.restaurante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCardapio {
    private Long itemCardapioId;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponivelApenasRestaurante;
    private String caminhoFoto;
    private Long cardapioId;

    public void atualizarDados(
            String nome,
            String descricao,
            Double preco,
            Boolean disponivelApenasRestaurante,
            String caminhoFoto
    ) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponivelApenasRestaurante = disponivelApenasRestaurante;
        this.caminhoFoto = caminhoFoto;
    }
}