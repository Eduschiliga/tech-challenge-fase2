package br.com.fiap.techchallengefase2.core.domain.restaurante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class Cardapio {
    private Long id;
    private Restaurante restaurante;
    private List<ItemCardapio> itens;
    private String nome;

    public void atualizarNome(String nome) {
        this.nome = nome;
    }
}
