package br.com.fiap.techchallengefase2.usuario.core.domain.restaurante;

public class Restaurante {
    private Long restauranteId;
    private String nome;

    public Restaurante(Long restaurantId, String nome) {
        this.restauranteId = restaurantId;
        this.nome = nome;
    }

    public Long getRestaurantId() {
        return restauranteId;
    }

    public String getNome() {
        return nome;
    }
}
