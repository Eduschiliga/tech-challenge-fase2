package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar.AtualizarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante.BuscarTodosCardapiosPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.criar.CriarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar.DeletarCardapio;
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
class CardapioControllerTest {

    @Mock
    private CriarCardapio criarCardapio;

    @Mock
    private AtualizarCardapio atualizarCardapio;

    @Mock
    private BuscarCardapioPorId buscarCardapioPorId;

    @Mock
    private BuscarTodosCardapiosPorRestaurante buscarTodosCardapiosPorRestaurante;

    @Mock
    private DeletarCardapio deletarCardapio;

    @InjectMocks
    private CardapioController cardapioController;

    @Test
    void criar_DeveRetornarIdDoCardapio() {
        Long usuarioLogadoId = 1L;
        CriarCardapioInputDTO input = mock(CriarCardapioInputDTO.class);
        when(criarCardapio.criar(usuarioLogadoId, input)).thenReturn(100L);

        Long result = cardapioController.criar(usuarioLogadoId, input);

        assertEquals(100L, result);
        verify(criarCardapio).criar(usuarioLogadoId, input);
    }

    @Test
    void atualizar_DeveRetornarIdDoCardapioAtualizado() {
        Long usuarioLogadoId = 1L;
        AtualizarCardapioInputDTO input = mock(AtualizarCardapioInputDTO.class);
        when(atualizarCardapio.atualizar(usuarioLogadoId, input)).thenReturn(100L);

        Long result = cardapioController.atualizar(usuarioLogadoId, input);

        assertEquals(100L, result);
        verify(atualizarCardapio).atualizar(usuarioLogadoId, input);
    }

    @Test
    void buscarPorId_DeveRetornarOutputDto() {
        Long cardapioId = 100L;
        Cardapio cardapio = mock(Cardapio.class);
        when(cardapio.getId()).thenReturn(cardapioId);
        when(cardapio.getNome()).thenReturn("Cardápio Principal");

        when(buscarCardapioPorId.buscarPorId(cardapioId)).thenReturn(cardapio);

        CardapioOutputDTO result = cardapioController.buscarPorId(cardapioId);

        assertNotNull(result);
        assertEquals(cardapioId, result.id());
        assertEquals("Cardápio Principal", result.nome());
        verify(buscarCardapioPorId).buscarPorId(cardapioId);
    }

    @Test
    void buscarTodosPorRestaurante_DeveRetornarListaDeDtos() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Cardapio cardapio = mock(Cardapio.class);
        when(cardapio.getId()).thenReturn(100L);
        when(cardapio.getNome()).thenReturn("Menu Degustação");

        when(buscarTodosCardapiosPorRestaurante.buscarTodos(usuarioLogadoId, restauranteId))
                .thenReturn(List.of(cardapio));

        List<CardapioOutputDTO> result = cardapioController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).id());
        verify(buscarTodosCardapiosPorRestaurante).buscarTodos(usuarioLogadoId, restauranteId);
    }

    @Test
    void deletarPorId_DeveChamarUseCase() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 100L;

        cardapioController.deletarPorId(usuarioLogadoId, cardapioId);

        verify(deletarCardapio).deletarPorId(usuarioLogadoId, cardapioId);
    }
}