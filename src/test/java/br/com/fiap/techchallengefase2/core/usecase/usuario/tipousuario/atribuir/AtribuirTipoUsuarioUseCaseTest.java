package br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuarioUseCase;
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
class AtribuirTipoUsuarioUseCaseTest {

    @InjectMocks
    private AtribuirTipoUsuarioUseCase atribuirTipoUsuarioUseCase;

    @Mock
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    @DisplayName("Deve atribuir o tipo de usuário com sucesso e salvar no gateway")
    void deveAtribuirTipoUsuarioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 10L;
        Long usuarioParaAtribuirId = 2L;

        TipoUsuario tipoUsuarioMock = mock(TipoUsuario.class);
        UsuarioBase usuarioParaAtribuirMock = mock(UsuarioBase.class);

        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuarioMock);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioParaAtribuirId)).thenReturn(usuarioParaAtribuirMock);

        atribuirTipoUsuarioUseCase.atribuirTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioParaAtribuirId);

        verify(usuarioParaAtribuirMock).atribuirTipoUsuario(tipoUsuarioMock);
        verify(usuarioGateway).salvar(usuarioParaAtribuirMock);
    }

    @Test
    @DisplayName("Deve propagar exceção quando o tipo de usuário for inválido ou o usuário logado não for dono")
    void devePropagarExcecaoQuandoValidacaoDoTipoFalhar() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 10L;
        Long usuarioParaAtribuirId = 2L;

        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                atribuirTipoUsuarioUseCase.atribuirTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioParaAtribuirId)
        );

        verify(buscarUsuarioPorIdUseCase, never()).buscarPorId(anyLong());
        verify(usuarioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve propagar exceção quando o usuário alvo não for encontrado")
    void devePropagarExcecaoQuandoUsuarioAlvoNaoEncontrado() {
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 10L;
        Long usuarioParaAtribuirId = 2L;

        TipoUsuario tipoUsuarioMock = mock(TipoUsuario.class);

        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuarioMock);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioParaAtribuirId))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                atribuirTipoUsuarioUseCase.atribuirTipoUsuario(usuarioLogadoId, tipoUsuarioId, usuarioParaAtribuirId)
        );

        verify(usuarioGateway, never()).salvar(any());
    }
}