package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTipoUsuarioPorRestautanteUseCaseTest {

    @InjectMocks
    private BuscarTipoUsuarioPorRestautanteUseCase buscarTipoUsuarioUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @Test
    @DisplayName("Deve retornar lista de tipos de usuário de um restaurante com sucesso")
    void deveBuscarTodosPorRestauranteComSucesso() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);
        List<TipoUsuario> tiposEsperados = List.of(
                new TipoUsuario(1L, restauranteId, "Gerente"),
                new TipoUsuario(2L, restauranteId, "Recepcionista")
        );

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(tipoUsuarioGateway.buscarTodosPorRestauranteId(restauranteId)).thenReturn(tiposEsperados);

        List<TipoUsuario> resultado = buscarTipoUsuarioUseCase.buscarTodosPorRestauranteId(usuarioLogadoId, restauranteId);

        assertEquals(2, resultado.size());
        assertEquals(tiposEsperados, resultado);

        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
        verify(tipoUsuarioGateway).buscarTodosPorRestauranteId(restauranteId);
    }

    @Test
    @DisplayName("Deve falhar quando o usuário logado não for dono do restaurante")
    void deveFalharQuandoNaoForDonoDoRestaurante() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);

        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        assertThrows(IllegalArgumentException.class, () ->
                buscarTipoUsuarioUseCase.buscarTodosPorRestauranteId(usuarioLogadoId, restauranteId)
        );

        verify(tipoUsuarioGateway, never()).buscarTodosPorRestauranteId(anyLong());
    }

    @Test
    @DisplayName("Deve propagar erro se a validação de perfil Dono falhar")
    void devePropagarErroValidacaoDono() {
        Long usuarioLogadoId = 1L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);

        doThrow(IllegalArgumentException.class)
                .when(validaSeUsuarioDono).validar(donoMock);

        assertThrows(IllegalArgumentException.class, () ->
                buscarTipoUsuarioUseCase.buscarTodosPorRestauranteId(usuarioLogadoId, 10L)
        );

        verify(validaSeUsuarioDonoRestaurante, never()).validar(any(), anyLong());
        verify(tipoUsuarioGateway, never()).buscarTodosPorRestauranteId(anyLong());
    }
}