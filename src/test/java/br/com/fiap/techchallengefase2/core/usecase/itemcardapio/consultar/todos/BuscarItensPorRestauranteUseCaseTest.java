package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarItensPorRestauranteUseCaseTest {

    @InjectMocks
    private BuscarItensPorRestauranteUseCase buscarItensPorRestauranteUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Test
    void deveBuscarTodosItensPorRestauranteComSucesso() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);
        List<ItemCardapio> itensEsperados = List.of(mock(ItemCardapio.class), mock(ItemCardapio.class));

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(itemCardapioGateway.buscarTodosPorRestauranteId(restauranteId)).thenReturn(itensEsperados);

        List<ItemCardapio> resultado = buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, restauranteId);

        assertEquals(itensEsperados.size(), resultado.size());
        assertEquals(itensEsperados, resultado);
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
        verify(itemCardapioGateway).buscarTodosPorRestauranteId(restauranteId);
    }

    @Test
    void devePropagarExcecaoQuandoUsuarioNaoForDono() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDono).validar(donoMock);

        assertThrows(IllegalArgumentException.class, () ->
                buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, restauranteId)
        );

        verify(itemCardapioGateway, never()).buscarTodosPorRestauranteId(anyLong());
    }

    @Test
    void devePropagarExcecaoQuandoNaoForDonoDoRestaurante() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        assertThrows(IllegalArgumentException.class, () ->
                buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, restauranteId)
        );

        verify(itemCardapioGateway, never()).buscarTodosPorRestauranteId(anyLong());
    }
}