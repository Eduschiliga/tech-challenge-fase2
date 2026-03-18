package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.CardapioController;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CardapioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio.AtualizarCardapioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio.CriarCardapioJson;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioRestControllerTest {

    @Mock
    private CardapioController cardapioController;

    @InjectMocks
    private CardapioRestController cardapioRestController;

    @Test
    void criar_DeveRetornarStatusCreatedEId() {
        CriarCardapioJson json = new CriarCardapioJson("Cardápio Verão");
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        when(cardapioController.criar(eq(usuarioLogadoId), argThat(input ->
                input.restauranteId().equals(restauranteId) &&
                        input.nome().equals("Cardápio Verão")
        ))).thenReturn(100L);

        ResponseEntity<Long> response = cardapioRestController.criar(usuarioLogadoId, restauranteId, json);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(100L, response.getBody());
        verify(cardapioController).criar(eq(usuarioLogadoId), argThat(input -> input.nome().equals("Cardápio Verão")));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() {
        AtualizarCardapioJson json = new AtualizarCardapioJson("Cardápio Inverno");
        Long usuarioLogadoId = 1L;
        Long cardapioId = 100L;

        when(cardapioController.atualizar(eq(usuarioLogadoId), argThat(input ->
                input.cardapioId().equals(cardapioId) &&
                        input.nome().equals("Cardápio Inverno")
        ))).thenReturn(cardapioId);

        ResponseEntity<Long> response = cardapioRestController.atualizar(usuarioLogadoId, cardapioId, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardapioId, response.getBody());
        verify(cardapioController).atualizar(eq(usuarioLogadoId), argThat(input -> input.cardapioId().equals(cardapioId)));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkECardapio() {
        Long cardapioId = 100L;
        CardapioOutputDTO output = mock(CardapioOutputDTO.class);

        when(cardapioController.buscarPorId(cardapioId)).thenReturn(output);

        ResponseEntity<CardapioOutputDTO> response = cardapioRestController.buscarPorId(cardapioId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(cardapioController).buscarPorId(cardapioId);
    }

    @Test
    void buscarTodosPorRestaurante_DeveRetornarStatusOkELista() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        List<CardapioOutputDTO> lista = List.of(mock(CardapioOutputDTO.class));

        when(cardapioController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId)).thenReturn(lista);

        ResponseEntity<List<CardapioOutputDTO>> response = cardapioRestController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(cardapioController).buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 100L;

        ResponseEntity<Void> response = cardapioRestController.deletar(usuarioLogadoId, cardapioId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cardapioController).deletarPorId(usuarioLogadoId, cardapioId);
    }
}