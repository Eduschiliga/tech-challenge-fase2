package br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarTipoUsuarioPorUsuarioUseCaseTest {

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @InjectMocks
    private BuscarTipoUsuarioPorUsuarioUseCase useCase;

    @Test
    void buscarPorUsuario_DeveRetornarApenasTiposQueODonoPossuiAcesso() {
        Long usuarioLogadoId = 1L;
        Long usuarioBuscadoId = 2L;
        Long restauranteIdComAcesso = 100L;
        Long restauranteIdSemAcesso = 200L;

        Dono donoLogado = mock(Dono.class);
        UsuarioBase usuarioBuscado = mock(UsuarioBase.class);
        TipoUsuario tipoComAcesso = mock(TipoUsuario.class);
        TipoUsuario tipoSemAcesso = mock(TipoUsuario.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoLogado);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioBuscadoId)).thenReturn(usuarioBuscado);

        when(tipoComAcesso.getRestauranteId()).thenReturn(restauranteIdComAcesso);
        when(tipoSemAcesso.getRestauranteId()).thenReturn(restauranteIdSemAcesso);
        when(usuarioBuscado.getTipoUsuarioList()).thenReturn(List.of(tipoComAcesso, tipoSemAcesso));

        when(donoLogado.isProprietario(restauranteIdComAcesso)).thenReturn(true);
        when(donoLogado.isProprietario(restauranteIdSemAcesso)).thenReturn(false);

        List<TipoUsuario> resultado = useCase.buscarPorUsuario(usuarioLogadoId, usuarioBuscadoId);

        assertEquals(1, resultado.size());
        assertEquals(tipoComAcesso, resultado.get(0));
        verify(validaSeUsuarioDono).validar(donoLogado);
    }

    @Test
    void buscarPorUsuario_DeveRetornarListaVaziaQuandoUsuarioBuscadoNaoTemTipos() {
        Long usuarioLogadoId = 1L;
        Long usuarioBuscadoId = 2L;

        Dono donoLogado = mock(Dono.class);
        UsuarioBase usuarioBuscado = mock(UsuarioBase.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoLogado);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioBuscadoId)).thenReturn(usuarioBuscado);
        when(usuarioBuscado.getTipoUsuarioList()).thenReturn(null);

        List<TipoUsuario> resultado = useCase.buscarPorUsuario(usuarioLogadoId, usuarioBuscadoId);

        assertTrue(resultado.isEmpty());
    }
}