package br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseTest {

    @InjectMocks
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Test
    @DisplayName("Deve buscar o restaurante por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restauranteMock));
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);

        Restaurante resultado = buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId);

        assertEquals(restauranteMock, resultado);
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
    }

    @Test
    @DisplayName("Deve propagar exceção quando usuário não for dono")
    void deveLancarExcecaoQuandoUsuarioNaoForDono() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(new IllegalArgumentException("Usuário não é da Categoria Dono"))
                .when(validaSeUsuarioDono).validar(donoMock);

        assertThrows(IllegalArgumentException.class, () ->
                buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId)
        );

        verify(restauranteGateway, never()).buscarPorId(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não for encontrado")
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId)
        );

        verify(validaSeUsuarioDonoRestaurante, never()).validar(any(), anyLong());
    }

    @Test
    @DisplayName("Deve propagar exceção quando dono não for proprietário do restaurante")
    void deveLancarExcecaoQuandoDonoNaoForProprietario() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restauranteMock));
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);

        doThrow(IllegalArgumentException.class)
                .when(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        assertThrows(IllegalArgumentException.class, () ->
                buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId)
        );
    }
}