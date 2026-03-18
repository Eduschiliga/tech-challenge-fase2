package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.TipoUsuarioController;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.TipoUsuarioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario.TipoUsuarioJson;
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
class TipoUsuarioRestControllerTest {

    @Mock
    private TipoUsuarioController tipoUsuarioController;

    @InjectMocks
    private TipoUsuarioRestController tipoUsuarioRestController;

    @Test
    void criar_DeveRetornarStatusCreatedEId() {
        TipoUsuarioJson json = new TipoUsuarioJson("Gerente", 10L);

        when(tipoUsuarioController.criar(eq(1L), argThat(input ->
                input.nome().equals("Gerente") &&
                        input.restauranteId().equals(10L)
        ))).thenReturn(5L);

        ResponseEntity<Long> response = tipoUsuarioRestController.criar(1L, json);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(5L, response.getBody());
        verify(tipoUsuarioController).criar(eq(1L), argThat(input ->
                input.nome().equals("Gerente") &&
                        input.restauranteId().equals(10L)
        ));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() {
        TipoUsuarioJson json = new TipoUsuarioJson("Atendente", null);

        when(tipoUsuarioController.atualizar(eq(1L), argThat(input ->
                input.nome().equals("Atendente") &&
                        input.tipoUsuarioId().equals(5L)
        ))).thenReturn(5L);

        ResponseEntity<Long> response = tipoUsuarioRestController.atualizar(1L, 5L, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5L, response.getBody());
        verify(tipoUsuarioController).atualizar(eq(1L), argThat(input ->
                input.nome().equals("Atendente") &&
                        input.tipoUsuarioId().equals(5L)
        ));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkETipoUsuario() {
        TipoUsuarioOutputDTO outputDTO = mock(TipoUsuarioOutputDTO.class);

        when(tipoUsuarioController.buscarPorId(eq(1L), eq(5L))).thenReturn(outputDTO);

        ResponseEntity<TipoUsuarioOutputDTO> response = tipoUsuarioRestController.buscarPorId(1L, 5L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tipoUsuarioController).buscarPorId(eq(1L), eq(5L));
    }

    @Test
    void buscarTodosPorRestaurante_DeveRetornarStatusOkELista() {
        List<TipoUsuarioOutputDTO> lista = List.of(mock(TipoUsuarioOutputDTO.class));

        when(tipoUsuarioController.buscarTodosPorRestaurante(eq(1L), eq(10L))).thenReturn(lista);

        ResponseEntity<List<TipoUsuarioOutputDTO>> response = tipoUsuarioRestController.buscarTodosPorRestaurante(1L, 10L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(tipoUsuarioController).buscarTodosPorRestaurante(eq(1L), eq(10L));
    }

    @Test
    void buscarTodosPorUsuario_DeveRetornarStatusOkELista() {
        List<TipoUsuarioOutputDTO> lista = List.of(mock(TipoUsuarioOutputDTO.class));

        when(tipoUsuarioController.buscarTodosPorUsuario(eq(1L), eq(2L))).thenReturn(lista);

        ResponseEntity<List<TipoUsuarioOutputDTO>> response = tipoUsuarioRestController.buscarTodosPorUsuario(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(tipoUsuarioController).buscarTodosPorUsuario(eq(1L), eq(2L));
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() {
        ResponseEntity<Void> response = tipoUsuarioRestController.deletar(1L, 5L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tipoUsuarioController).deletarTipoUsuario(eq(1L), eq(5L));
    }
}