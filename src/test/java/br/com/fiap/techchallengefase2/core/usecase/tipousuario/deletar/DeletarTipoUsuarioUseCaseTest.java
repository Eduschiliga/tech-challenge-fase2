package br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.exception.tipousuario.TipoUsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarTipoUsuarioUseCaseTest {

    @InjectMocks
    private DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    @Mock
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @Test
    @DisplayName("Deve deletar um tipo de usuário com sucesso")
    void deveDeletarTipoUsuarioComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 100L;

        Dono donoMock = mock(Dono.class);
        TipoUsuario tipoUsuarioMock = new TipoUsuario(tipoUsuarioId, 10L, "Gerente");

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuarioMock);

        // Act
        deletarTipoUsuarioUseCase.deletarPorId(usuarioLogadoId, tipoUsuarioId);

        // Assert
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(tipoUsuarioGateway, times(1)).deletarPorId(tipoUsuarioId);
    }

    @Test
    @DisplayName("Deve falhar e não deletar se o utilizador não for do tipo Dono")
    void deveFalharQuandoUsuarioNaoForDono() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Dono usuarioComumMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(usuarioComumMock);

        // Simula a falha da regra de negócio
        doThrow(UsuarioNaoDonoException.class).when(validaSeUsuarioDono).validar(usuarioComumMock);

        // Act & Assert
        assertThrows(UsuarioNaoDonoException.class, () ->
                deletarTipoUsuarioUseCase.deletarPorId(usuarioLogadoId, 100L)
        );

        verify(tipoUsuarioGateway, never()).deletarPorId(anyLong());
        verify(buscarTipoUsuarioPorIdUseCase, never()).buscarPorId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Deve propagar erro se o tipo de usuário não for encontrado")
    void devePropagarErroSeTipoNaoExistir() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 999L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId))
                .thenThrow(TipoUsuarioNaoEncontradoException.class);

        // Act & Assert
        assertThrows(TipoUsuarioNaoEncontradoException.class, () ->
                deletarTipoUsuarioUseCase.deletarPorId(usuarioLogadoId, tipoUsuarioId)
        );

        verify(tipoUsuarioGateway, never()).deletarPorId(anyLong());
    }
}