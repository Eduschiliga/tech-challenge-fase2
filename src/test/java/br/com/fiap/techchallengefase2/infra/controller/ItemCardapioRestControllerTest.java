package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.ItemCardapioController;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio.ItemCardapioJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioRestControllerTest {

    @Mock
    private ItemCardapioController itemCardapioController;

    @InjectMocks
    private ItemCardapioRestController itemCardapioRestController;

    @Test
    void criar_DeveRetornarStatusCreatedEId() {
        ItemCardapioJson json = new ItemCardapioJson("Burger", "Desc", 25.0, true, "/foto.png");
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;

        when(itemCardapioController.criar(eq(usuarioLogadoId), eq(cardapioId), argThat(input ->
                input.nome().equals("Burger") &&
                        input.descricao().equals("Desc") &&
                        input.preco().equals(25.0) &&
                        input.disponivelApenasRestaurante().equals(true) &&
                        input.caminhoFoto().equals("/foto.png")
        ))).thenReturn(100L);

        ResponseEntity<Long> response = itemCardapioRestController.criar(usuarioLogadoId, cardapioId, json);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(100L, response.getBody());
        verify(itemCardapioController).criar(eq(usuarioLogadoId), eq(cardapioId), any());
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() {
        ItemCardapioJson json = new ItemCardapioJson("Burger Pro", "Nova Desc", 30.0, false, "/foto2.png");
        Long usuarioLogadoId = 1L;
        Long itemId = 100L;

        when(itemCardapioController.atualizar(eq(usuarioLogadoId), eq(itemId), argThat(input ->
                input.nome().equals("Burger Pro") &&
                        input.descricao().equals("Nova Desc") &&
                        input.preco().equals(30.0) &&
                        input.disponivelApenasRestaurante().equals(false) &&
                        input.caminhoFoto().equals("/foto2.png")
        ))).thenReturn(itemId);

        ResponseEntity<Long> response = itemCardapioRestController.atualizar(usuarioLogadoId, itemId, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemId, response.getBody());
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkEItem() {
        Long usuarioLogadoId = 1L;
        Long itemId = 100L;
        ItemCardapioOutputDTO output = mock(ItemCardapioOutputDTO.class);

        when(itemCardapioController.buscarPorId(usuarioLogadoId, itemId)).thenReturn(output);

        ResponseEntity<ItemCardapioOutputDTO> response = itemCardapioRestController.buscarPorId(usuarioLogadoId, itemId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(itemCardapioController).buscarPorId(usuarioLogadoId, itemId);
    }

    @Test
    void buscarTodosPorCardapio_DeveRetornarStatusOkELista() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        List<ItemCardapioOutputDTO> lista = List.of(mock(ItemCardapioOutputDTO.class));

        when(itemCardapioController.buscarTodosPorCardapio(usuarioLogadoId, cardapioId)).thenReturn(lista);

        ResponseEntity<List<ItemCardapioOutputDTO>> response = itemCardapioRestController.buscarTodosPorCardapio(usuarioLogadoId, cardapioId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(itemCardapioController).buscarTodosPorCardapio(usuarioLogadoId, cardapioId);
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() {
        Long usuarioLogadoId = 1L;
        Long itemId = 100L;

        ResponseEntity<Void> response = itemCardapioRestController.deletar(usuarioLogadoId, itemId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(itemCardapioController).deletarPorId(usuarioLogadoId, itemId);
    }
}