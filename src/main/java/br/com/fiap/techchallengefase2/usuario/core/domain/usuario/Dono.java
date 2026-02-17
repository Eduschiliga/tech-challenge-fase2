package br.com.fiap.techchallengefase2.usuario.core.domain.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.restaurante.Restaurante;
import lombok.Getter;

import java.util.List;

import static br.com.fiap.techchallengefase2.usuario.core.domain.factory.UsuarioFactory.TIPO_USUARIO_DONO;

@Getter
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
        super(usuarioId, nome, email, login, senha, endereco, TIPO_USUARIO_DONO);
        this.restaurantes = restaurantes;
    }

    public boolean isProprietario(Long restaurantId) {
        return restaurantes.stream().anyMatch(r -> r.getRestaurantId().equals(restaurantId));
    }

    public void adicionarRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public void inativarRestaurantes() {
        if (this.restaurantes == null || this.restaurantes.isEmpty()) return;

        this.restaurantes.forEach(Restaurante::inativar);
    }

    public void ativarRestaurantes() {
        if (this.restaurantes == null || this.restaurantes.isEmpty()) return;

        this.restaurantes.forEach(Restaurante::ativar);
    }
}
