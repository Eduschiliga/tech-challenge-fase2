package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.AtualizarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.CriarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.TipoUsuarioOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario.BuscarTipoUsuarioPorUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuario;
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
class TipoUsuarioControllerTest {

    @Mock
    private CriarTipoUsuario criarTipoUsuario;
    @Mock
    private AtualizarTipoUsuario atualizarTipoUsuario;
    @Mock
    private BuscarTipoUsuarioPorId buscarTipoUsuarioPorId;
    @Mock
    private DeletarTipoUsuario deletarTipoUsuario;
    @Mock
    private BuscarTipoUsuarioPorRestaurante buscarTipoUsuarioPorRestaurante;
    @Mock
    private BuscarTipoUsuarioPorUsuario buscarTipoUsuarioPorUsuario;

    @InjectMocks
    private TipoUsuarioController tipoUsuarioController;

    @Test
    void criar_DeveRetornarIdDoTipoUsuario() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 100L;
        String nome = "Gerente";

        // Ajustado para bater com o restauranteId 100L
        CriarTipoUsuarioInputDTO input = new CriarTipoUsuarioInputDTO(nome, restauranteId);
        when(criarTipoUsuario.criar(usuarioLogadoId, restauranteId, nome)).thenReturn(500L);

        Long result = tipoUsuarioController.criar(usuarioLogadoId, input);

        assertEquals(500L, result);
        verify(criarTipoUsuario).criar(usuarioLogadoId, restauranteId, nome);
    }

    @Test
    void atualizar_DeveRetornarIdDoTipoUsuarioAtualizado() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 100L;
        String nome = "Gerente Sênior";

        AtualizarTipoUsuarioInputDTO input = new AtualizarTipoUsuarioInputDTO(nome, tipoUsuarioId);
        when(atualizarTipoUsuario.atualizar(usuarioLogadoId, tipoUsuarioId, nome)).thenReturn(tipoUsuarioId);

        Long result = tipoUsuarioController.atualizar(usuarioLogadoId, input);

        assertEquals(tipoUsuarioId, result);
        verify(atualizarTipoUsuario).atualizar(usuarioLogadoId, tipoUsuarioId, nome);
    }

    @Test
    void buscarPorId_DeveRetornarOutputDto() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 100L;
        Long restauranteId = 10L;

        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        when(tipoUsuario.getTipoUsuarioId()).thenReturn(tipoUsuarioId);
        when(tipoUsuario.getNome()).thenReturn("Gerente");
        when(tipoUsuario.getRestauranteId()).thenReturn(restauranteId); // Adicionado para evitar o "Actual: 0"

        when(buscarTipoUsuarioPorId.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuario);

        TipoUsuarioOutputDTO result = tipoUsuarioController.buscarPorId(usuarioLogadoId, tipoUsuarioId);

        assertNotNull(result);
        assertEquals(tipoUsuarioId, result.tipoUsuarioId());
        assertEquals("Gerente", result.nome());
        assertEquals(restauranteId, result.restauranteId()); // Verificação extra
    }

    @Test
    void buscarTodosPorRestaurante_DeveRetornarListaDeDtos() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        TipoUsuario tipo = mock(TipoUsuario.class);
        when(tipo.getTipoUsuarioId()).thenReturn(100L);
        when(tipo.getNome()).thenReturn("Admin");
        when(tipo.getRestauranteId()).thenReturn(restauranteId);

        when(buscarTipoUsuarioPorRestaurante.buscarTodosPorRestauranteId(usuarioLogadoId, restauranteId))
                .thenReturn(List.of(tipo));

        List<TipoUsuarioOutputDTO> result = tipoUsuarioController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).tipoUsuarioId());
        verify(buscarTipoUsuarioPorRestaurante).buscarTodosPorRestauranteId(usuarioLogadoId, restauranteId);
    }

    @Test
    void buscarTodosPorUsuario_DeveRetornarListaDeDtos() {
        Long usuarioLogadoId = 1L;
        Long usuarioId = 2L;
        TipoUsuario tipo = mock(TipoUsuario.class);
        when(tipo.getTipoUsuarioId()).thenReturn(100L);
        when(tipo.getNome()).thenReturn("Operador");
        when(tipo.getRestauranteId()).thenReturn(10L);

        when(buscarTipoUsuarioPorUsuario.buscarPorUsuario(usuarioLogadoId, usuarioId))
                .thenReturn(List.of(tipo));

        List<TipoUsuarioOutputDTO> result = tipoUsuarioController.buscarTodosPorUsuario(usuarioLogadoId, usuarioId);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).tipoUsuarioId());
        verify(buscarTipoUsuarioPorUsuario).buscarPorUsuario(usuarioLogadoId, usuarioId);
    }

    @Test
    void deletarTipoUsuario_DeveChamarUseCase() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 100L;

        tipoUsuarioController.deletarTipoUsuario(usuarioLogadoId, tipoUsuarioId);

        verify(deletarTipoUsuario).deletarPorId(usuarioLogadoId, tipoUsuarioId);
    }
}