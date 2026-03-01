package br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarRestauranteUseCaseTest {

    @InjectMocks
    private DeletarRestauranteUseCase deletarRestauranteUseCase;

    @Mock
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Test
    @DisplayName("Deve deletar o restaurante com sucesso")
    void deveDeletarRestauranteComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Restaurante restauranteMock = mock(Restaurante.class);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);

        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId))
                .thenReturn(restauranteMock);

        // Act
        deletarRestauranteUseCase.deletarPorId(usuarioLogadoId, restauranteId);

        // Assert
        verify(buscarRestaurantePorIdUseCase).buscarPorId(usuarioLogadoId, restauranteId);
        verify(restauranteGateway, times(1)).deletarPorId(restauranteId);
    }

    @Test
    @DisplayName("Deve propagar exceção caso a busca ou validação falhe (Restaurante não encontrado ou sem permissão)")
    void devePropagarExcecaoQuandoBuscaFalhar() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                deletarRestauranteUseCase.deletarPorId(usuarioLogadoId, restauranteId)
        );


        verify(restauranteGateway, never()).deletarPorId(anyLong());
    }
}