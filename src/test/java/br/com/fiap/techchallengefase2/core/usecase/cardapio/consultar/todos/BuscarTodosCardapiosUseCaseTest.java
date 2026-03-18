package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
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
class BuscarTodosCardapiosUseCaseTest {

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private BuscarTodosCardapiosUseCase buscarTodosCardapiosUseCase;

    @Test
    void buscarTodos_DeveRetornarListaDeCardapios() {
        Long usuarioLogadoId = 1L;
        List<Cardapio> cardapiosEsperados = List.of(
                mock(Cardapio.class),
                mock(Cardapio.class)
        );

        when(cardapioGateway.buscarTodos()).thenReturn(cardapiosEsperados);

        List<Cardapio> result = buscarTodosCardapiosUseCase.buscarTodos(usuarioLogadoId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cardapiosEsperados, result);
        verify(cardapioGateway).buscarTodos();
    }
}