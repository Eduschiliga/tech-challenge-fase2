package br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.exception.cardapio.CardapioNaoEncontraoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarCardapioPorIdUseCaseTest {

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Test
    void buscarPorId_DeveRetornarCardapioQuandoEncontrado() {
        Long cardapioId = 1L;
        Cardapio cardapioEsperado = mock(Cardapio.class);

        when(cardapioGateway.buscarPorId(cardapioId)).thenReturn(Optional.of(cardapioEsperado));

        Cardapio result = buscarCardapioPorIdUseCase.buscarPorId(cardapioId);

        assertNotNull(result);
        assertEquals(cardapioEsperado, result);
        verify(cardapioGateway).buscarPorId(cardapioId);
    }

    @Test
    void buscarPorId_DeveLancarExcecaoQuandoNaoEncontrado() {
        Long cardapioId = 2L;

        when(cardapioGateway.buscarPorId(cardapioId)).thenReturn(Optional.empty());

        assertThrows(CardapioNaoEncontraoException.class, () ->
                buscarCardapioPorIdUseCase.buscarPorId(cardapioId)
        );
        verify(cardapioGateway).buscarPorId(cardapioId);
    }
}