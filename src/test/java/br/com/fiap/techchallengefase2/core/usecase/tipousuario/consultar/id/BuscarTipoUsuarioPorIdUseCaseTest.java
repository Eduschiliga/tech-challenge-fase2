package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTipoUsuarioPorIdUseCaseTest {

    @InjectMocks
    private BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @Test
    @DisplayName("Deve retornar tipo de usuário com sucesso")
    void deveRetornarTipoUsuarioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long tipoId = 10L;
        Long restauranteId = 100L;

        Dono donoMock = mock(Dono.class);
        TipoUsuario tipoMock = new TipoUsuario(tipoId, restauranteId, "Gerente");

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(tipoUsuarioGateway.buscarPorId(tipoId)).thenReturn(Optional.of(tipoMock));

        TipoUsuario resultado = buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoId);

        assertNotNull(resultado);
        assertEquals(tipoId, resultado.getTipoUsuarioId());
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo de usuário não existir")
    void deveLancarExcecaoQuandoNaoEncontrado() {
        Long usuarioLogadoId = 1L;
        Long tipoId = 99L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(tipoUsuarioGateway.buscarPorId(tipoId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoId)
        );

        verify(validaSeUsuarioDonoRestaurante, never()).validar(any(), anyLong());
    }

    @Test
    @DisplayName("Deve propagar erro quando dono não for proprietário do restaurante")
    void devePropagarErroDonoNaoProprietario() {
        Long usuarioLogadoId = 1L;
        Long tipoId = 10L;
        Long restauranteId = 100L;

        Dono donoMock = mock(Dono.class);
        TipoUsuario tipoMock = new TipoUsuario(tipoId, restauranteId, "Gerente");

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(tipoUsuarioGateway.buscarPorId(tipoId)).thenReturn(Optional.of(tipoMock));

        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        assertThrows(IllegalArgumentException.class, () ->
                buscarTipoUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId, tipoId)
        );
    }
}