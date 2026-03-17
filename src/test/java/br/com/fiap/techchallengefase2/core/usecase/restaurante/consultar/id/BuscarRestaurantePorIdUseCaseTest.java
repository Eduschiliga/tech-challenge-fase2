package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase; // Mocked, mas não usado, veja a nota de melhoria

    @InjectMocks
    private BuscarRestaurantePorIdUseCase useCase;

    private Restaurante restaurante;
    private Long restauranteId;
    private Long usuarioLogadoId;

    @BeforeEach
    void setUp() {
        restauranteId = 1L;
        usuarioLogadoId = 10L;
        restaurante = new Restaurante(restauranteId, "Restaurante Teste", "Endereço", "Cozinha", "Horário", 100L);
    }

    @Test
    void deveBuscarRestaurantePorId_quandoEncontrado() {
        // Given
        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restaurante));

        // When
        Restaurante result = useCase.buscarPorId(usuarioLogadoId, restauranteId);

        // Then
        assertNotNull(result);
        assertEquals(restaurante, result);
        verify(restauranteGateway, times(1)).buscarPorId(restauranteId);
    }

    @Test
    void deveLancarExcecao_quandoRestauranteNaoEncontrado() {
        // Given
        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RestauranteNaoEncontradoException.class, () -> {
            useCase.buscarPorId(usuarioLogadoId, restauranteId);
        });

        verify(restauranteGateway, times(1)).buscarPorId(restauranteId);
    }
}