package br.com.fiap.techchallengefase2.usuario.core.usecase.usuario.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.todos.BuscarTodosUsuariosUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTodosUsuariosUseCaseTest {

    @InjectMocks
    private BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    void deveRetornarListaDeUsuariosQuandoExistiremUsuarios() {
        // Arrange
        List<UsuarioBase> usuarios = new ArrayList<>();

        Cliente usuario1 = new Cliente(1L, null, null, null, null, null);

        Cliente usuario2 = new Cliente(2L, null, null, null, null, null);

        usuarios.add(usuario1);
        usuarios.add(usuario2);

        when(usuarioGateway.buscarTodos()).thenReturn(usuarios);

        // Act
        Collection<UsuarioBase> resultado = buscarTodosUsuariosUseCase.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(usuario1));
        assertTrue(resultado.contains(usuario2));
        verify(usuarioGateway, times(1)).buscarTodos();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremUsuarios() {
        // Arrange
        when(usuarioGateway.buscarTodos()).thenReturn(new ArrayList<>());

        // Act
        Collection<UsuarioBase> resultado = buscarTodosUsuariosUseCase.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioGateway, times(1)).buscarTodos();
    }
}