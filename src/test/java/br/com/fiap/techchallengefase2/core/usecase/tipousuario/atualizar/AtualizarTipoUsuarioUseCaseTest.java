package br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarTipoUsuarioUseCaseTest {

    @InjectMocks
    private AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase;

    @Mock
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @Test
    @DisplayName("Deve atualizar o nome do tipo de usuário com sucesso")
    void deveAtualizarTipoUsuarioComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 50L;
        String novoNome = "Gerente Geral";
        Long idEsperado = 50L;

        Dono donoMock = mock(Dono.class);
        TipoUsuario tipoUsuarioExistente = new TipoUsuario(tipoUsuarioId, 10L, "Gerente Antigo");

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId)).thenReturn(tipoUsuarioExistente);
        when(tipoUsuarioGateway.salvar(any(TipoUsuario.class))).thenReturn(idEsperado);

        // Act
        Long resultado = atualizarTipoUsuarioUseCase.atualizar(usuarioLogadoId, tipoUsuarioId, novoNome);

        // Assert
        assertEquals(idEsperado, resultado);
        assertEquals(novoNome, tipoUsuarioExistente.getNome()); // Verifica se o domínio foi alterado

        verify(validaSeUsuarioDono).validar(donoMock); // Garante a validação de perfil
        verify(tipoUsuarioGateway).salvar(tipoUsuarioExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário logado não for um Dono")
    void deveLancarExcecaoQuandoNaoForDono() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);

        // Simula falha na regra de negócio
        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDono).validar(donoMock);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                atualizarTipoUsuarioUseCase.atualizar(usuarioLogadoId, 50L, "Novo Nome")
        );

        verify(tipoUsuarioGateway, never()).salvar(any());
        verify(buscarTipoUsuarioPorIdUseCase, never()).buscarPorId(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Deve propagar erro quando o tipo de usuário não for encontrado")
    void devePropagarErroQuandoTipoNaoExistir() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long tipoUsuarioId = 999L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoUsuarioId))
                .thenThrow(IllegalArgumentException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                atualizarTipoUsuarioUseCase.atualizar(usuarioLogadoId, tipoUsuarioId, "Nome")
        );

        verify(tipoUsuarioGateway, never()).salvar(any());
    }
}