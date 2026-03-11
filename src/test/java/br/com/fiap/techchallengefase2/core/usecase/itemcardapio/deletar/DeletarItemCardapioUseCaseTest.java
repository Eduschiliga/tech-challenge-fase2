package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarItemCardapioUseCaseTest {

    @InjectMocks
    private DeletarItemCardapioUseCase deletarItemCardapioUseCase;

    @Mock
    private BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Test
    void deveDeletarItemCardapioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        ItemCardapio itemMock = mock(ItemCardapio.class);
        when(itemMock.getItemCardapioId()).thenReturn(itemCardapioId);

        when(buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId)).thenReturn(itemMock);

        deletarItemCardapioUseCase.deletarPorId(usuarioLogadoId, itemCardapioId);

        verify(buscarItemCardapioPorIdUseCase).buscarPorId(usuarioLogadoId, itemCardapioId);
        verify(itemCardapioGateway).deletarPorId(itemCardapioId);
    }

    @Test
    void devePropagarExcecaoQuandoBuscaFalharOuSemPermissao() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        when(buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                deletarItemCardapioUseCase.deletarPorId(usuarioLogadoId, itemCardapioId)
        );

        verify(itemCardapioGateway, never()).deletarPorId(anyLong());
    }
}