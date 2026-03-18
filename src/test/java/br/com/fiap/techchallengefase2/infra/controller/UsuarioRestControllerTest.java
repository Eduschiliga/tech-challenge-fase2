package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.UsuarioController;
import br.com.fiap.techchallengefase2.core.dto.usuario.UsuarioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario.DesvincularUsuarioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario.VincularUsuarioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.usuario.AtualizarSenhaJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.usuario.AtualizarUsuarioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.usuario.UsuarioJson;
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
class UsuarioRestControllerTest {

    @Mock
    private UsuarioController usuarioController;

    @InjectMocks
    private UsuarioRestController usuarioRestController;

    @Test
    void criar_DeveRetornarStatusCreatedEIdDoUsuario() {
        UsuarioJson json = new UsuarioJson("Nome", "Endereco", "email@email.com", "login", "senha", 1);

        when(usuarioController.criar(argThat(input ->
                input.getNome().equals("Nome") &&
                        input.getEndereco().equals("Endereco") &&
                        input.getEmail().equals("email@email.com") &&
                        input.getLogin().equals("login") &&
                        input.getSenha().equals("senha") &&
                        input.getCategoriaUsuario().equals(1)
        ))).thenReturn(1L);

        ResponseEntity<Long> response = usuarioRestController.criar(json);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody());

        verify(usuarioController).criar(argThat(input ->
                input.getNome().equals("Nome") &&
                        input.getEmail().equals("email@email.com")
        ));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEUsuarioAtualizado() {
        AtualizarUsuarioJson json = new AtualizarUsuarioJson("Novo Nome", "novo@email.com", "novologin", "Novo Endereco");
        UsuarioOutputDTO outputDTO = mock(UsuarioOutputDTO.class);

        when(usuarioController.atualizarDadosParciaisUsuario(eq(1L), argThat(input ->
                input.getNome().equals("Novo Nome") &&
                        input.getEmail().equals("novo@email.com") &&
                        input.getLogin().equals("novologin") &&
                        input.getEndereco().equals("Novo Endereco")
        ))).thenReturn(outputDTO);

        ResponseEntity<UsuarioOutputDTO> response = usuarioRestController.atualizar(1L, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(usuarioController).atualizarDadosParciaisUsuario(eq(1L), argThat(input ->
                input.getNome().equals("Novo Nome")
        ));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkEUsuario() {
        UsuarioOutputDTO outputDTO = mock(UsuarioOutputDTO.class);
        when(usuarioController.buscarUsuarioPorId(1L, 2L)).thenReturn(outputDTO);

        ResponseEntity<UsuarioOutputDTO> response = usuarioRestController.buscarPorId(1L, 2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioController).buscarUsuarioPorId(1L, 2L);
    }

    @Test
    void buscarTodos_DeveRetornarStatusOkEListaDeUsuarios() {
        List<UsuarioOutputDTO> lista = List.of(mock(UsuarioOutputDTO.class));
        when(usuarioController.buscarTodosUsuarios(1L)).thenReturn(lista);

        ResponseEntity<List<UsuarioOutputDTO>> response = usuarioRestController.buscarTodos(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(usuarioController).buscarTodosUsuarios(1L);
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() {
        ResponseEntity<Void> response = usuarioRestController.deletar(1L, 2L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioController).deletarUsuarioPorId(1L, 2L);
    }

    @Test
    void atribuirTipoUsuario_DeveRetornarStatusOk() {
        VincularUsuarioJson json = new VincularUsuarioJson(10L, 20L);

        ResponseEntity<Void> response = usuarioRestController.atribuirTipoUsuario(1L, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioController).atribuirTipoUsuario(eq(1L), argThat(input ->
                input.tipoUsuarioId().equals(10L) &&
                        input.usuarioParaAtribuirId().equals(20L)
        ));
    }

    @Test
    void removerTipoUsuario_DeveRetornarStatusOk() {
        DesvincularUsuarioJson json = new DesvincularUsuarioJson(10L, 20L);

        ResponseEntity<Void> response = usuarioRestController.removerTipoUsuario(1L, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioController).removerTipoUsuario(eq(1L), argThat(input ->
                input.tipoUsuarioId().equals(10L) &&
                        input.usuarioParaAtribuirId().equals(20L)
        ));
    }

    @Test
    void atualizarSenhaUsuario_DeveRetornarStatusOk() {
        AtualizarSenhaJson json = new AtualizarSenhaJson("novaSenha", "senhaAntiga");

        ResponseEntity<Void> response = usuarioRestController.atualizarSenhaUsuario(1L, json);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioController).atualizarSenhaUsuario(eq(1L), argThat(input ->
                input.getNovaSenha().equals("novaSenha") &&
                        input.getSenhaAtual().equals("senhaAntiga")
        ));
    }
}