package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.DesvincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.VincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.UsuarioOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private DeletarUsuario deletarUsuario;
    @Mock
    private CriarUsuario criarUsuarioUseCase;
    @Mock
    private BuscarUsuarioPorId buscarUsuarioPorId;
    @Mock
    private BuscarTodosUsuarios buscarTodosUsuarios;
    @Mock
    private AtualizarSenhaUsuario atualizarSenhaUsuario;
    @Mock
    private AtualizarUsuario atualizarUsuario;
    @Mock
    private AtribuirTipoUsuario atribuirTipoUsuario;
    @Mock
    private RemoverTipoUsuario removerTipoUsuario;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void atribuirTipoUsuario_DeveChamarUseCase() {
        VincularUsuarioInputDTO input = new VincularUsuarioInputDTO(1L, 2L);
        usuarioController.atribuirTipoUsuario(10L, input);
        verify(atribuirTipoUsuario).atribuirTipoUsuario(10L, 1L, 2L);
    }

    @Test
    void removerTipoUsuario_DeveChamarUseCase() {
        DesvincularUsuarioInputDTO input = new DesvincularUsuarioInputDTO(1L, 2L);
        usuarioController.removerTipoUsuario(10L, input);
        verify(removerTipoUsuario).removerTipoUsuario(10L, 1L, 2L);
    }

    @Test
    void deletarUsuarioPorId_DeveChamarUseCase() {
        usuarioController.deletarUsuarioPorId(10L, 1L);
        verify(deletarUsuario).deletarPorId(10L, 1L);
    }

    @Test
    void criar_DeveRetornarIdDoUsuario() {
        CriarUsuarioInputDTO input = new CriarUsuarioInputDTO("Nome", "email@test.com", "login", "senha", "endereco", 0);
        UsuarioBase usuarioMock = mock(UsuarioBase.class);
        when(usuarioMock.getUsuarioId()).thenReturn(100L);
        when(criarUsuarioUseCase.criar(any())).thenReturn(usuarioMock);

        Long result = usuarioController.criar(input);

        assertEquals(100L, result);
        verify(criarUsuarioUseCase).criar(any());
    }

    @Test
    void buscarTodosUsuarios_DeveRetornarListaDeDtos() {
        UsuarioBase u1 = mock(UsuarioBase.class);
        when(u1.getUsuarioId()).thenReturn(1L);
        when(u1.getNome()).thenReturn("U1");
        when(u1.getEmail()).thenReturn("u1@test.com");

        when(buscarTodosUsuarios.buscarTodos()).thenReturn(List.of(u1));

        List<UsuarioOutputDTO> result = usuarioController.buscarTodosUsuarios(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(buscarTodosUsuarios).buscarTodos();
    }

    @Test
    void buscarUsuarioPorId_DeveRetornarDto() {
        UsuarioBase u1 = mock(UsuarioBase.class);
        when(u1.getUsuarioId()).thenReturn(1L);
        when(u1.getNome()).thenReturn("U1");
        when(u1.getEmail()).thenReturn("u1@test.com");
        when(buscarUsuarioPorId.buscarPorId(1L)).thenReturn(u1);

        UsuarioOutputDTO result = usuarioController.buscarUsuarioPorId(10L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.usuarioId());
        verify(buscarUsuarioPorId).buscarPorId(1L);
    }

    @Test
    void atualizarSenhaUsuario_DeveRetornarDto() {
        AtualizarSenhaInputDTO input = mock(AtualizarSenhaInputDTO.class);
        UsuarioBase u1 = mock(UsuarioBase.class);
        when(u1.getUsuarioId()).thenReturn(1L);
        when(u1.getNome()).thenReturn("U1");
        when(u1.getEmail()).thenReturn("u1@test.com");
        when(atualizarSenhaUsuario.atualizar(10L, input)).thenReturn(u1);

        UsuarioOutputDTO result = usuarioController.atualizarSenhaUsuario(10L, input);

        assertNotNull(result);
        assertEquals(1L, result.usuarioId());
        verify(atualizarSenhaUsuario).atualizar(10L, input);
    }

    @Test
    void atualizarDadosParciaisUsuario_DeveRetornarDto() {
        DadosUsuarioInputDTO input = mock(DadosUsuarioInputDTO.class);
        UsuarioBase u1 = mock(UsuarioBase.class);
        when(u1.getUsuarioId()).thenReturn(1L);
        when(u1.getNome()).thenReturn("U1");
        when(u1.getEmail()).thenReturn("u1@test.com");
        when(atualizarUsuario.atualizar(10L, input)).thenReturn(u1);

        UsuarioOutputDTO result = usuarioController.atualizarDadosParciaisUsuario(10L, input);

        assertNotNull(result);
        assertEquals(1L, result.usuarioId());
        verify(atualizarUsuario).atualizar(10L, input);
    }
}