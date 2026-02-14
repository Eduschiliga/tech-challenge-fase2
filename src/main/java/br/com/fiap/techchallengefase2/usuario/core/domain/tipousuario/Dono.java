package br.com.fiap.techchallengefase2.usuario.core.domain.tipousuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.restaurante.Restaurante;

import java.util.List;

public class Dono extends TipoUsuarioBase {
    private List<Restaurante> restaurantes;

    public Dono(Long id, String nomeTipo) {
        super(id, nomeTipo);
    }

    public boolean isProprietario(Long restaurantId) {
        return restaurantes.stream().anyMatch(r -> r.getRestaurantId().equals(restaurantId));
    }

    public void adicionarRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }
}
