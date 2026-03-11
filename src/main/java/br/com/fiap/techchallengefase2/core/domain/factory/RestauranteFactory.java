package br.com.fiap.techchallengefase2.core.domain.factory;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;

public class RestauranteFactory {

    private RestauranteFactory() {
        throw new IllegalArgumentException("Restaurante Factory não pode ser instanciada");
    }

    public static Restaurante atualizar(
            Long restauranteId,
            String nome,
            String endereco,
            String tipoCozinha,
            String horarioFuncionamento,
            Long usuarioId
    ) {
        return new Restaurante(
                restauranteId,
                nome,
                endereco,
                tipoCozinha,
                horarioFuncionamento,
                usuarioId
        );
    }
}
