package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar.AtualizarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.idusuario.BuscarRestaurantePorUsuarioId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarTodosRestaurantes;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.criar.CriarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar.DeletarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class RestauranteConfigTest {

    private final RestauranteConfig restauranteConfig = new RestauranteConfig();
    private final RestauranteGateway restauranteGateway = mock(RestauranteGateway.class);
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
    private final ValidaSeUsuarioDono validaSeUsuarioDono = mock(ValidaSeUsuarioDono.class);
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase = mock(BuscarRestaurantePorIdUseCase.class);

    @Test
    void buscarRestaurantePorIdUseCase_DeveInstanciarCorretamente() {
        var bean = restauranteConfig.buscarRestaurantePorIdUseCase(buscarUsuarioPorIdUseCase, restauranteGateway);
        assertNotNull(bean);
    }

    @Test
    void buscarRestaurantePorId_DeveRetornarInstancia() {
        BuscarRestaurantePorId bean = restauranteConfig.buscarRestaurantePorId(buscarRestaurantePorIdUseCase);
        assertNotNull(bean);
    }

    @Test
    void buscarRestaurantePorUsuarioId_DeveInstanciarCorretamente() {
        BuscarRestaurantePorUsuarioId bean = restauranteConfig.buscarRestaurantePorUsuarioId(
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDono,
                restauranteGateway
        );
        assertNotNull(bean);
    }

    @Test
    void buscarTodosRestaurantes_DeveInstanciarCorretamente() {
        BuscarTodosRestaurantes bean = restauranteConfig.buscarTodosRestaurantes(restauranteGateway);
        assertNotNull(bean);
    }

    @Test
    void criarRestaurante_DeveInstanciarCorretamente() {
        CriarRestaurante bean = restauranteConfig.criarRestaurante(
                buscarUsuarioPorIdUseCase,
                restauranteGateway,
                validaSeUsuarioDono
        );
        assertNotNull(bean);
    }

    @Test
    void atualizarRestaurante_DeveInstanciarCorretamente() {
        AtualizarRestaurante bean = restauranteConfig.atualizarRestaurante(buscarRestaurantePorIdUseCase, restauranteGateway);
        assertNotNull(bean);
    }

    @Test
    void deletarRestaurante_DeveInstanciarCorretamente() {
        DeletarRestaurante bean = restauranteConfig.deletarRestaurante(buscarRestaurantePorIdUseCase, restauranteGateway);
        assertNotNull(bean);
    }
}