package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.exception.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.exception.ValidacaoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Mock
    private CardapioGateway cardapioGateway;

    @Test
    void deveBuscarTodosItensPorRestauranteComSucesso() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        Long restauranteId = 20L;

        Dono usuarioMock = mock(Dono.class);
        Cardapio cardapioMock = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioMock);
        when(cardapioGateway.buscarPorId(cardapioId)).thenReturn(Optional.of(cardapioMock));
        when(cardapioMock.getRestaurante()).thenReturn(restauranteMock);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);

        List<ItemCardapio> itensEsperados = List.of(mock(ItemCardapio.class), mock(ItemCardapio.class));

        when(itemCardapioGateway.buscarTodosPorCardapioId(cardapioId)).thenReturn(itensEsperados);

        List<ItemCardapio> resultado = buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, cardapioId);

        assertEquals(itensEsperados, resultado);
        verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioLogadoId);
        verify(validaSeUsuarioDono).validar(usuarioMock);
        verify(validaSeUsuarioDonoRestaurante).validar(usuarioMock, restauranteId);
        verify(itemCardapioGateway).buscarTodosPorCardapioId(cardapioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForDono() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;

        Dono usuarioMock = mock(Dono.class);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioMock);
        doThrow(UsuarioNaoDonoException.class).when(validaSeUsuarioDono).validar(usuarioMock);

        assertThrows(UsuarioNaoDonoException.class, () ->
                buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, cardapioId)
        );

        verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioLogadoId);
        verify(validaSeUsuarioDono).validar(usuarioMock);
        verify(itemCardapioGateway, never()).buscarTodosPorCardapioId(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioOuRestauranteIdForemNulos() {
        Long usuarioLogadoId = 1L;

        assertThrows(ValidacaoException.class, () ->
                buscarItensPorRestauranteUseCase.buscarTodos(null, 10L)
        );

        assertThrows(ValidacaoException.class, () ->
                buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, null)
        );

        verify(buscarUsuarioPorIdUseCase, never()).buscarPorId(anyLong());
        verify(validaSeUsuarioDono, never()).validar(any());
        verify(itemCardapioGateway, never()).buscarTodosPorCardapioId(anyLong());
    }
}
