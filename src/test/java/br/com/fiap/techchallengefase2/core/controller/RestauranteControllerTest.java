package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.dto.restaurante.RestauranteOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar.AtualizarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.idusuario.BuscarRestaurantePorUsuarioId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarTodosRestaurantes;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.criar.CriarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar.DeletarRestaurante;
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
class RestauranteControllerTest {

    @Mock
    private CriarRestaurante criarRestaurante;
    @Mock
    private AtualizarRestaurante atualizarRestaurante;
    @Mock
    private BuscarRestaurantePorId buscarRestaurantePorId;
    @Mock
    private DeletarRestaurante deletarRestaurante;
    @Mock
    private BuscarRestaurantePorUsuarioId buscarRestaurantePorUsuarioId;
    @Mock
    private BuscarTodosRestaurantes buscarTodosRestaurantes;

    @InjectMocks
    private RestauranteController restauranteController;

    @Test
    void criar_DeveRetornarId() {
        Long usuarioLogadoId = 1L;
        DadosRestauranteInputDTO input = mock(DadosRestauranteInputDTO.class);
        when(criarRestaurante.criar(usuarioLogadoId, input)).thenReturn(100L);

        Long result = restauranteController.criar(usuarioLogadoId, input);

        assertEquals(100L, result);
        verify(criarRestaurante).criar(usuarioLogadoId, input);
    }

    @Test
    void atualizar_DeveRetornarId() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;
        DadosRestauranteInputDTO input = mock(DadosRestauranteInputDTO.class);
        when(atualizarRestaurante.atualizar(usuarioLogadoId, restauranteId, input)).thenReturn(restauranteId);

        Long result = restauranteController.atualizar(usuarioLogadoId, restauranteId, input);

        assertEquals(restauranteId, result);
        verify(atualizarRestaurante).atualizar(usuarioLogadoId, restauranteId, input);
    }

    @Test
    void buscarPorId_DeveRetornarRestauranteOutputDTO() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getRestauranteId()).thenReturn(restauranteId);
        when(restaurante.getNome()).thenReturn("Restaurante Teste");

        when(buscarRestaurantePorId.buscarPorId(usuarioLogadoId, restauranteId)).thenReturn(restaurante);

        RestauranteOutputDTO result = restauranteController.buscarPorId(usuarioLogadoId, restauranteId);

        assertNotNull(result);
        assertEquals(restauranteId, result.restauranteId());
        verify(buscarRestaurantePorId).buscarPorId(usuarioLogadoId, restauranteId);
    }

    @Test
    void buscarTodosPorUsuarioId_DeveRetornarListaDeDTOs() {
        Long usuarioLogadoId = 1L;
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getRestauranteId()).thenReturn(100L);
        when(restaurante.getNome()).thenReturn("Restaurante Teste");

        when(buscarRestaurantePorUsuarioId.buscarTodos(usuarioLogadoId)).thenReturn(List.of(restaurante));

        List<RestauranteOutputDTO> result = restauranteController.buscarTodosPorUsuarioId(usuarioLogadoId);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).restauranteId());
        verify(buscarRestaurantePorUsuarioId).buscarTodos(usuarioLogadoId);
    }

    @Test
    void buscarTodos_DeveRetornarListaDeTodosOsRestaurantes() {
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getRestauranteId()).thenReturn(200L);
        when(restaurante.getNome()).thenReturn("Global Rest");

        when(buscarTodosRestaurantes.buscarTodos()).thenReturn(List.of(restaurante));

        List<RestauranteOutputDTO> result = restauranteController.buscarTodos();

        assertEquals(1, result.size());
        assertEquals(200L, result.get(0).restauranteId());
        verify(buscarTodosRestaurantes).buscarTodos();
    }

    @Test
    void deletarPorId_DeveChamarUseCase() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;

        restauranteController.deletarPorId(usuarioLogadoId, restauranteId);

        verify(deletarRestaurante).deletarPorId(usuarioLogadoId, restauranteId);
    }
}