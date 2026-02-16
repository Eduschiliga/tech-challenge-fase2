package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.restaurante.Restaurante;

import java.util.List;

public class Dono extends UsuarioBase {
    private List<Restaurante> restaurantes;

    public Dono(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco,
            List<Restaurante> restaurantes
    ) {
        super(usuarioId, nome, email, login, senha, endereco);
        this.restaurantes = restaurantes;
    }

    public boolean isProprietario(Long restaurantId) {
        return restaurantes.stream().anyMatch(r -> r.getRestaurantId().equals(restaurantId));
    }

    public void adicionarRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }
}
