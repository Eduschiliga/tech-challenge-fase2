package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.RestauranteController;
import br.com.fiap.techchallengefase2.core.dto.restaurante.RestauranteOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.restaurante.RestauranteJson;
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
class RestauranteRestControllerTest {

    @Mock
    private RestauranteController restauranteController;

    @InjectMocks
    private RestauranteRestController restauranteRestController;

    @Test
    void criar_DeveRetornarStatusCreatedEId() {
        RestauranteJson json = new RestauranteJson("Sabor Local", "Rua A", "Brasileira", "10h-22h");
        Long usuarioLogadoId = 1L;

        when(restauranteController.criar(eq(usuarioLogadoId), argThat(input ->
                input.nome().equals("Sabor Local") &&
                        input.endereco().equals("Rua A") &&
                        input.tipoCozinha().equals("Brasileira") &&
                        input.horarioFuncionamento().equals("10h-22h")
        ))).thenReturn(100L);

        ResponseEntity<Long> response = restauranteRestController.criar(usuarioLogadoId, json);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(100L, response.getBody());
        verify(restauranteController).criar(eq(usuarioLogadoId), argThat(input -> input.nome().equals("Sabor Local")));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEId() {
        RestauranteJson json = new RestauranteJson("Sabor Local Alterado", "Rua B", "Italiana", "11h-23h");
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;

        when(restauranteController.atualizar(eq(usuarioLogadoId), eq(restauranteId), argThat(input ->
                input.nome().equals("Sabor Local Alterado") &&
                        input.endereco().equals("Rua B") &&
                        input.tipoCozinha().equals("Italiana") &&
                        input.horarioFuncionamento().equals("11h-23h")
        ))).thenReturn(restauranteId);

        ResponseEntity<Long> response = restauranteRestController.atualizar(usuarioLogadoId, restauranteId, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restauranteId, response.getBody());
        verify(restauranteController).atualizar(eq(usuarioLogadoId), eq(restauranteId), argThat(input -> input.nome().equals("Sabor Local Alterado")));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkERestaurante() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;
        RestauranteOutputDTO output = mock(RestauranteOutputDTO.class);

        when(restauranteController.buscarPorId(usuarioLogadoId, restauranteId)).thenReturn(output);

        ResponseEntity<RestauranteOutputDTO> response = restauranteRestController.buscarPorId(usuarioLogadoId, restauranteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(restauranteController).buscarPorId(usuarioLogadoId, restauranteId);
    }

    @Test
    void buscarTodosPorUsuario_DeveRetornarStatusOkELista() {
        Long usuarioLogadoId = 1L;
        List<RestauranteOutputDTO> lista = List.of(mock(RestauranteOutputDTO.class));

        when(restauranteController.buscarTodosPorUsuarioId(usuarioLogadoId)).thenReturn(lista);

        ResponseEntity<List<RestauranteOutputDTO>> response = restauranteRestController.buscarTodosPorUsuario(usuarioLogadoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(restauranteController).buscarTodosPorUsuarioId(usuarioLogadoId);
    }

    @Test
    void buscarTodos_DeveRetornarStatusOkEListaCompleta() {
        List<RestauranteOutputDTO> lista = List.of(mock(RestauranteOutputDTO.class), mock(RestauranteOutputDTO.class));

        when(restauranteController.buscarTodos()).thenReturn(lista);

        ResponseEntity<List<RestauranteOutputDTO>> response = restauranteRestController.buscarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(restauranteController).buscarTodos();
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;

        ResponseEntity<Void> response = restauranteRestController.deletar(usuarioLogadoId, restauranteId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restauranteController).deletarPorId(usuarioLogadoId, restauranteId);
    }
}