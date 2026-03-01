package br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar;

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

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Test
    @DisplayName("Deve criar um novo tipo de usuário com sucesso")
    void deveCriarTipoUsuarioComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        String nomeTipo = "Gerente de Vendas";
        Long idGerado = 100L;

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(tipoUsuarioGateway.salvar(any(TipoUsuario.class))).thenReturn(idGerado);

        // Act
        Long resultado = criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, nomeTipo);

        // Assert
        assertEquals(idGerado, resultado);

        // Verifica se as validações de regra de negócio foram chamadas
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        // Verifica se o objeto foi persistido corretamente
        verify(tipoUsuarioGateway).salvar(argThat(tipo ->
                tipo.getNome().equals(nomeTipo) &&
                        tipo.getRestauranteId().equals(restauranteId)
        ));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do tipo for nulo")
    void deveLancarExcecaoQuandoNomeForNulo() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, null)
        );

        verify(tipoUsuarioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do tipo for apenas espaços")
    void deveLancarExcecaoQuandoNomeForVazio() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                criarTipoUsuarioUseCase.criar(usuarioLogadoId, restauranteId, "   ")
        );

        verify(tipoUsuarioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve propagar exceção se o usuário não for dono (ValidaSeUsuarioDono falhar)")
    void devePropagarExcecaoDeValidacaoDeDono() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(IllegalArgumentException.class)
                .when(validaSeUsuarioDono).validar(donoMock);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                criarTipoUsuarioUseCase.criar(usuarioLogadoId, 10L, "Admin")
        );

        verify(tipoUsuarioGateway, never()).salvar(any());
    }
}