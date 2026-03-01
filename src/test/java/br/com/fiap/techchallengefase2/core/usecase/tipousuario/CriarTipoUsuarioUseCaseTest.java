package br.com.fiap.techchallengefase2.core.usecase.tipousuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarTipoUsuarioUseCaseTest {

    @InjectMocks
    private CriarTipoUsuarioUseCase criarTipoUsuarioUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @Test
    void deveCriarTipoUsuarioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        String nomeTipo = "Gerente";
        Long idEsperado = 99L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(donoMock.isProprietario(restauranteId)).thenReturn(true);
        when(tipoUsuarioGateway.salvar(any(TipoUsuario.class))).thenReturn(idEsperado);

        Long resultado = criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, nomeTipo);

        assertEquals(idEsperado, resultado);

        ArgumentCaptor<TipoUsuario> captor = ArgumentCaptor.forClass(TipoUsuario.class);
        verify(tipoUsuarioGateway).salvar(captor.capture());

        TipoUsuario tipoCapturado = captor.getValue();
        assertEquals(restauranteId, tipoCapturado.getRestauranteId());
        assertEquals(nomeTipo, tipoCapturado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNaoForDonoDoRestaurante() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        String nomeTipo = "Gerente";

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(donoMock.isProprietario(restauranteId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () ->
                criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, nomeTipo)
        );

        verify(tipoUsuarioGateway, never()).salvar(any(TipoUsuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(donoMock.isProprietario(restauranteId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, null)
        );

        verify(tipoUsuarioGateway, never()).salvar(any(TipoUsuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(donoMock.isProprietario(restauranteId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, "   ")
        );

        verify(tipoUsuarioGateway, never()).salvar(any(TipoUsuario.class));
    }
}