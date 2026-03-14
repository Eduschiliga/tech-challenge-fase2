package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.exception.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarItemCardapioPorIdUseCaseTest {

    @InjectMocks
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

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
    void deveBuscarItemCardapioPorIdComSucesso() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        Long cardapioId = 50L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);
        ItemCardapio itemMock = mock(ItemCardapio.class);
        Cardapio cardapioMock = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemMock));
        when(itemMock.getCardapioId()).thenReturn(cardapioId);
        when(cardapioGateway.buscarPorId(cardapioId)).thenReturn(Optional.of(cardapioMock));
        when(cardapioMock.getRestaurante()).thenReturn(restauranteMock);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);

        ItemCardapio resultado = buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId);

        assertEquals(itemMock, resultado);
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
    }

    @Test
    void devePropagarExcecaoQuandoUsuarioNaoForDono() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(UsuarioNaoDonoException.class).when(validaSeUsuarioDono).validar(donoMock);

        assertThrows(UsuarioNaoDonoException.class, () ->
                buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)
        );

        verify(itemCardapioGateway, never()).buscarPorId(anyLong());
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.empty());

        assertThrows(ItemCardapioNaoEncontradoException.class, () ->
                buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)
        );

        verify(validaSeUsuarioDonoRestaurante, never()).validar(any(), anyLong());
    }

    @Test
    void devePropagarExcecaoQuandoNaoForDonoDoRestaurante() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        Long cardapioId = 50L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);
        ItemCardapio itemMock = mock(ItemCardapio.class);
        Cardapio cardapioMock = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);


        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemMock));
        when(itemMock.getCardapioId()).thenReturn(cardapioId);
        when(cardapioGateway.buscarPorId(cardapioId)).thenReturn(Optional.of(cardapioMock));
        when(cardapioMock.getRestaurante()).thenReturn(restauranteMock);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);

        doThrow(UsuarioNaoDonoException.class).when(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        assertThrows(UsuarioNaoDonoException.class, () ->
                buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)
        );
    }
}
