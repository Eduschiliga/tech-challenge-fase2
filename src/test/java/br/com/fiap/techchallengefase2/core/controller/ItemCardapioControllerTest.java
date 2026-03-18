package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos.BuscarItensPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar.CriarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar.DeletarItemCardapio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioControllerTest {

    @Mock
    private CriarItemCardapio criarItemCardapio;
    @Mock
    private AtualizarItemCardapio atualizarItemCardapio;
    @Mock
    private BuscarItemCardapioPorId buscarItemCardapioPorId;
    @Mock
    private BuscarItensPorRestaurante buscarItensPorRestaurante;
    @Mock
    private DeletarItemCardapio deletarItemCardapio;

    @InjectMocks
    private ItemCardapioController itemCardapioController;

    @Test
    void criar_DeveRetornarIdDoNovoItem() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        DadosItemCardapioInputDTO input = mock(DadosItemCardapioInputDTO.class);
        when(criarItemCardapio.criar(usuarioLogadoId, cardapioId, input)).thenReturn(100L);

        Long result = itemCardapioController.criar(usuarioLogadoId, cardapioId, input);

        assertEquals(100L, result);
    }

    @Test
    void atualizar_DeveRetornarIdDoItemAtualizado() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        DadosItemCardapioInputDTO input = mock(DadosItemCardapioInputDTO.class);
        when(atualizarItemCardapio.atualizar(usuarioLogadoId, itemCardapioId, input)).thenReturn(itemCardapioId);

        Long result = itemCardapioController.atualizar(usuarioLogadoId, itemCardapioId, input);

        assertEquals(itemCardapioId, result);
    }

    @Test
    void buscarPorId_DeveRetornarOutputDto() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;
        Long cardapioPaiId = 10L;

        ItemCardapio item = mock(ItemCardapio.class);
        // Verifique se no seu Domain o método é getId() ou getItemCardapioId()
        // e se no DTO o campo é id() ou cardapioId()
        when(item.getItemCardapioId()).thenReturn(itemCardapioId);
        when(item.getCardapioId()).thenReturn(cardapioPaiId);
        when(item.getNome()).thenReturn("Hambúrguer");
        when(item.getPreco()).thenReturn(10.0);

        when(buscarItemCardapioPorId.buscarPorId(usuarioLogadoId, itemCardapioId)).thenReturn(item);

        ItemCardapioOutputDTO result = itemCardapioController.buscarPorId(usuarioLogadoId, itemCardapioId);

        assertNotNull(result);
        assertEquals(itemCardapioId, result.itemCardapioId());
        assertEquals(cardapioPaiId, result.cardapioId());
    }

    @Test
    void buscarTodosPorCardapio_DeveRetornarListaDeDtos() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        ItemCardapio item = mock(ItemCardapio.class);

        when(item.getItemCardapioId()).thenReturn(100L);
        when(item.getCardapioId()).thenReturn(cardapioId);
        when(item.getNome()).thenReturn("Pizza");
        when(item.getPreco()).thenReturn(50.0);

        when(buscarItensPorRestaurante.buscarTodos(usuarioLogadoId, cardapioId)).thenReturn(List.of(item));

        List<ItemCardapioOutputDTO> result = itemCardapioController.buscarTodosPorCardapio(usuarioLogadoId, cardapioId);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).itemCardapioId());
        assertEquals(cardapioId, result.get(0).cardapioId());
    }

    @Test
    void deletarPorId_DeveChamarUseCase() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 100L;

        itemCardapioController.deletarPorId(usuarioLogadoId, itemCardapioId);

        verify(deletarItemCardapio).deletarPorId(usuarioLogadoId, itemCardapioId);
    }
}