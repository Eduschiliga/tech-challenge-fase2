package br.com.fiap.techchallengefase2.infra.config;

import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar.AtualizarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar.AtualizarRestauranteUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarRestaurantePorUsuarioId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarRestaurantePorUsuarioIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarTodosRestaurantes;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarTodosRestaurantesUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.criar.CriarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.criar.CriarRestauranteUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar.DeletarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar.DeletarRestauranteUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestauranteConfig {

    @Bean
    public BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            RestauranteGateway restauranteGateway
    ) {
        return new BuscarRestaurantePorIdUseCase(buscarUsuarioPorIdUseCase, restauranteGateway);
    }

    @Bean
    public BuscarRestaurantePorId buscarRestaurantePorId(BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase) {
        return buscarRestaurantePorIdUseCase;
    }

    @Bean
    public BuscarRestaurantePorUsuarioId buscarRestaurantePorUsuarioId(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            RestauranteGateway restauranteGateway
    ) {
        return new BuscarRestaurantePorUsuarioIdUseCase(buscarUsuarioPorIdUseCase, validaSeUsuarioDono, restauranteGateway);
    }

    @Bean
    public BuscarTodosRestaurantes buscarTodosRestaurantes(RestauranteGateway restauranteGateway) {
        return new BuscarTodosRestaurantesUseCase(restauranteGateway);
    }

    @Bean
    public CriarRestaurante criarRestaurante(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            RestauranteGateway restauranteGateway,
            ValidaSeUsuarioDono validaSeUsuarioDono
    ) {
        return new CriarRestauranteUseCase(buscarUsuarioPorIdUseCase, restauranteGateway, validaSeUsuarioDono);
    }

    @Bean
    public AtualizarRestaurante atualizarRestaurante(
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
            RestauranteGateway restauranteGateway
    ) {
        return new AtualizarRestauranteUseCase(buscarRestaurantePorIdUseCase, restauranteGateway);
    }

    @Bean
    public DeletarRestaurante deletarRestaurante(
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
            RestauranteGateway restauranteGateway
    ) {
        return new DeletarRestauranteUseCase(buscarRestaurantePorIdUseCase, restauranteGateway);
    }
}
