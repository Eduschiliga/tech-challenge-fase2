package br.com.fiap.techchallengefase2.core.domain.factory;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;

public class RestauranteFactory {

    private RestauranteFactory() {
        throw new IllegalArgumentException("Restaurante Factory não pode ser instanciada");
    }

    public static Restaurante atualizar(
            Restaurante restaurante,
            DadosRestauranteInputDTO dadosRestauranteInputDTO
    ) {
        return new Restaurante(
                restaurante.getRestauranteId(),
                dadosRestauranteInputDTO.nome(),
                dadosRestauranteInputDTO.endereco(),
                dadosRestauranteInputDTO.tipoCozinha(),
                dadosRestauranteInputDTO.horarioFuncionamento(),
                restaurante.getUsuarioId()
        );
    }
}
