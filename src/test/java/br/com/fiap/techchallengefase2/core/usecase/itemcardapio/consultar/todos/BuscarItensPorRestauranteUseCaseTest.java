package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarItensPorRestauranteUseCaseTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private BuscarItensPorRestauranteUseCase buscarItensPorRestauranteUseCase;

    @Test
    void buscarTodos_DeveRetornarListaDeItensDoCardapio() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        List<ItemCardapio> itensEsperados = List.of(
                mock(ItemCardapio.class),
                mock(ItemCardapio.class)
        );

        when(itemCardapioGateway.buscarTodosPorCardapioId(cardapioId)).thenReturn(itensEsperados);

        List<ItemCardapio> result = buscarItensPorRestauranteUseCase.buscarTodos(usuarioLogadoId, cardapioId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(itensEsperados, result);
        verify(itemCardapioGateway).buscarTodosPorCardapioId(cardapioId);
    }
}