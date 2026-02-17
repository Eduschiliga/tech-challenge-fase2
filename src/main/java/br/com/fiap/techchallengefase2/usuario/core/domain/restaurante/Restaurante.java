package br.com.fiap.techchallengefase2.usuario.core.domain.restaurante;

public class Restaurante {
    private Long restauranteId;
    private String nome;
    private boolean ativo;

    public Restaurante(Long restaurantId, String nome) {
        this.restauranteId = restaurantId;
        this.nome = nome;
        this.ativo = true;
    }

    public Long getRestaurantId() {
        return restauranteId;
    }

    public String getNome() {
        return nome;
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}
