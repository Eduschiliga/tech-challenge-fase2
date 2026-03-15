package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


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
            List<Restaurante> restauranteList
    ) {
        super(usuarioId, nome, email, login, senha, endereco, CategoriaUsuario.DONO.getCodigo(), new ArrayList<>());
        this.restaurantes = restauranteList;
    }

    public Dono(
            Long usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            String endereco,
            List<Restaurante> restaurantes,
            List<TipoUsuario> tipoUsuarioList
    ) {
        super(usuarioId, nome, email, login, senha, endereco, CategoriaUsuario.DONO.getCodigo(), tipoUsuarioList);
        this.restaurantes = restaurantes;
    }

    public boolean isProprietario(Long restaurantId) {
        return restaurantes.stream().anyMatch(r -> r.getRestauranteId().equals(restaurantId));
    }

    public void adicionarRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }
}
