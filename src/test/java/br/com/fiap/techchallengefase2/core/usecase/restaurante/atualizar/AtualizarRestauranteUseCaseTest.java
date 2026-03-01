package br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteUseCaseTest {

    @InjectMocks
    private AtualizarRestauranteUseCase atualizarRestauranteUseCase;

    @Mock
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Test
    @DisplayName("Deve atualizar o restaurante com sucesso")
    void deveAtualizarRestauranteComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Long idEsperado = 10L;

        Restaurante restauranteMock = mock(Restaurante.class);
        DadosRestauranteInputDTO dadosDTO = mock(DadosRestauranteInputDTO.class);

        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);
        when(restauranteMock.getUsuarioId()).thenReturn(usuarioLogadoId);

        when(dadosDTO.nome()).thenReturn("Novo Nome");
        when(dadosDTO.endereco()).thenReturn("Novo Endereco");
        when(dadosDTO.tipoCozinha()).thenReturn("Japonesa");
        when(dadosDTO.horarioFuncionamento()).thenReturn("19h-23h");

        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId)).thenReturn(restauranteMock);
        when(restauranteGateway.salvar(any(Restaurante.class))).thenReturn(idEsperado);

        // Act
        Long resultado = atualizarRestauranteUseCase.atualizar(usuarioLogadoId, restauranteId, dadosDTO);

        // Assert
        assertEquals(idEsperado, resultado);

        verify(restauranteGateway).salvar(argThat(restaurante ->
                restaurante.getNome().equals("Novo Nome") &&
                        restaurante.getRestauranteId().equals(restauranteId)
        ));
    }

    @Test
    @DisplayName("Deve propagar exceção quando o usuário não for do tipo Dono ou não for proprietário")
    void devePropagarExcecaoQuandoNaoForDonoOuProprietario() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        DadosRestauranteInputDTO dadosDTO = mock(DadosRestauranteInputDTO.class);

        // Como a regra foi delegada para a busca, precisamos mockar a busca estourando o erro
        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                atualizarRestauranteUseCase.atualizar(usuarioLogadoId, restauranteId, dadosDTO)
        );

        verify(restauranteGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve propagar exceção quando restaurante não for encontrado")
    void devePropagarExcecaoQuandoRestauranteInvalido() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        DadosRestauranteInputDTO dadosDTO = mock(DadosRestauranteInputDTO.class);

        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                atualizarRestauranteUseCase.atualizar(usuarioLogadoId, restauranteId, dadosDTO)
        );

        verify(restauranteGateway, never()).salvar(any());
    }
}