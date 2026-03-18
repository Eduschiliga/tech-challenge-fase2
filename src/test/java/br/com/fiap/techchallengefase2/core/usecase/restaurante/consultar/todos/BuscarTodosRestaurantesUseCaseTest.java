package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTodosRestaurantesUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private BuscarTodosRestaurantesUseCase buscarTodosRestaurantesUseCase;

    @Test
    void buscarTodos_DeveRetornarListaDeRestaurantes() {
        List<Restaurante> restaurantesEsperados = List.of(
                mock(Restaurante.class),
                mock(Restaurante.class)
        );

        when(restauranteGateway.buscarTodos()).thenReturn(restaurantesEsperados);

        List<Restaurante> result = buscarTodosRestaurantesUseCase.buscarTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(restaurantesEsperados, result);
        verify(restauranteGateway).buscarTodos();
    }
}