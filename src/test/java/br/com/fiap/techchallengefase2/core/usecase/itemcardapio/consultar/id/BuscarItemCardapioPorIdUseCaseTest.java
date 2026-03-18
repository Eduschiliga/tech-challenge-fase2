package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.exception.cardapio.ItemCardapioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
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
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Test
    void deveBuscarItemCardapioPorIdComSucesso() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        ItemCardapio itemMock = mock(ItemCardapio.class);

        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemMock));

        ItemCardapio resultado = buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId);

        assertEquals(itemMock, resultado);
    }

    @Test
    void deveLancarExcecaoQuandoItemNaoEncontrado() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.empty());

        assertThrows(ItemCardapioNaoEncontradoException.class, () ->
                buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)
        );

        verify(validaSeUsuarioDonoRestaurante, never()).validar(any(), anyLong());
    }
}
