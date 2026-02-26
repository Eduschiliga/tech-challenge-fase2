package br.com.fiap.techchallengefase2.usuario.core.usecase.deletar;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.consultar.id.BuscarUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.deletar.DeletarUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarUsuarioUseCaseTest {

    @InjectMocks
    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Test
    void deveDeletarUsuarioQuandoUsuarioLogadoEhODonoDoRegistro() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long usuarioId = 1L;

        Cliente usuario = new Cliente(usuarioId, null, null, null, null, null);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);

        // Act
        assertDoesNotThrow(() -> deletarUsuarioUseCase.deletarPorId(usuarioLogadoId, usuarioId));

        // Assert
        verify(usuarioGateway, times(1)).deletarPorId(usuarioId);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEhODonoDoRegistro() {
        // Arrange
        Long usuarioLogadoId = 2L;
        Long usuarioId = 1L;

        Cliente usuario = new Cliente(usuarioId, null, null, null, null, null);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuario);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deletarUsuarioUseCase.deletarPorId(usuarioLogadoId, usuarioId));

        verify(usuarioGateway, never()).deletarPorId(anyLong());
    }
}