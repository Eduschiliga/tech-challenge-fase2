package br.com.fiap.techchallengefase2.usuario.core.usecase.usuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIdUseCaseTest {

    @InjectMocks
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        // Arrange
        Long usuarioId = 1L;
        Cliente usuario = new Cliente(usuarioId, null, null, null, null, null);

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioBase resultado = buscarUsuarioPorIdUseCase.buscarPorId(usuarioId);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getUsuarioId());
        verify(usuarioGateway, times(1)).buscarPorId(usuarioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        // Arrange
        Long usuarioId = 1L;

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> buscarUsuarioPorIdUseCase.buscarPorId(usuarioId));

        verify(usuarioGateway, times(1)).buscarPorId(usuarioId);
    }
}