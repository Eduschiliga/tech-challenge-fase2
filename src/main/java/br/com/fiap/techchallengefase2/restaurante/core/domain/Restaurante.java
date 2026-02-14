package br.com.fiap.techchallengefase2.restaurante.core.domain;

public class Restaurante {
    private Long restauranteId;
    private String nome;

    public Restaurante(Long restaurantId, String nome) {
        this.restauranteId = restaurantId;
        this.nome = nome;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public String getNome() {
        return nome;
    }
}
