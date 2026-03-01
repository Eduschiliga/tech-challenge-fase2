package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorUsuarioIdUseCaseTest {

    @InjectMocks
    private BuscarRestaurantePorUsuarioIdUseCase buscarRestaurantePorUsuarioIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Test
    @DisplayName("Deve buscar todos os restaurantes do usuário com sucesso")
    void deveBuscarTodosRestaurantesComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;

        UsuarioBase usuarioMock = mock(UsuarioBase.class);
        List<Restaurante> restaurantesMock = List.of(
                mock(Restaurante.class),
                mock(Restaurante.class)
        );

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioMock);
        when(restauranteGateway.buscarTodosPorUsuarioId(usuarioLogadoId)).thenReturn(restaurantesMock);

        // Act
        List<Restaurante> resultado = buscarRestaurantePorUsuarioIdUseCase.buscarTodos(usuarioLogadoId);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(restaurantesMock, resultado);

        verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioLogadoId);
        verify(validaSeUsuarioDono).validar(usuarioMock);
        verify(restauranteGateway).buscarTodosPorUsuarioId(usuarioLogadoId);
    }

    @Test
    @DisplayName("Deve propagar exceção quando o usuário não for do tipo Dono")
    void devePropagarExcecaoQuandoNaoForDono() {
        // Arrange
        Long usuarioLogadoId = 1L;
        UsuarioBase usuarioMock = mock(UsuarioBase.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioMock);
        doThrow(IllegalArgumentException.class)
                .when(validaSeUsuarioDono).validar(usuarioMock);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                buscarRestaurantePorUsuarioIdUseCase.buscarTodos(usuarioLogadoId)
        );

        verify(restauranteGateway, never()).buscarTodosPorUsuarioId(anyLong());
    }

    @Test
    @DisplayName("Deve propagar exceção quando a busca de usuário falhar")
    void devePropagarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Long usuarioLogadoId = 1L;

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                buscarRestaurantePorUsuarioIdUseCase.buscarTodos(usuarioLogadoId)
        );

        verify(validaSeUsuarioDono, never()).validar(any());
        verify(restauranteGateway, never()).buscarTodosPorUsuarioId(anyLong());
    }
}