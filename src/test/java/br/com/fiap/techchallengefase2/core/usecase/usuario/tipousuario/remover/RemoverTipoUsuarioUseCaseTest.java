package br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoverTipoUsuarioUseCaseTest {

    @InjectMocks
    private RemoverTipoUsuarioUseCase removerTipoUsuarioUseCase;

    @Mock
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    @DisplayName("Deve remover o tipo de usuário com sucesso e salvar no gateway")
    void deveRemoverTipoUsuarioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 10L;
        Long usuarioAlvoId = 2L;

        TipoUsuario tipoUsuarioMock = mock(TipoUsuario.class);
        UsuarioBase usuarioAlvoMock = mock(UsuarioBase.class);

        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuarioMock);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioAlvoId)).thenReturn(usuarioAlvoMock);

        removerTipoUsuarioUseCase.removerTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioAlvoId);

        verify(usuarioAlvoMock).removerTipoUsuario(tipoUsuarioMock);
        verify(usuarioGateway).salvar(usuarioAlvoMock);
    }

    @Test
    @DisplayName("Deve propagar exceção quando a validação do tipo falhar (ex: sem permissão ou tipo não existe)")
    void devePropagarExcecaoQuandoValidacaoDoTipoFalhar() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 10L;
        Long usuarioAlvoId = 2L;

        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                removerTipoUsuarioUseCase.removerTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioAlvoId)
        );

        verify(buscarUsuarioPorIdUseCase, never()).buscarPorId(anyLong());
        verify(usuarioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve propagar exceção quando o usuário alvo não for encontrado")
    void devePropagarExcecaoQuandoUsuarioAlvoNaoEncontrado() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 10L;
        Long usuarioAlvoId = 2L;

        TipoUsuario tipoUsuarioMock = mock(TipoUsuario.class);

        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuarioMock);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioAlvoId))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                removerTipoUsuarioUseCase.removerTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioAlvoId)
        );

        verify(usuarioGateway, never()).salvar(any());
    }
}