package br.com.fiap.techchallengefase2.core.usecase.restaurante.criar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.exception.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.gateway.RestauranteGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
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
class CriarRestauranteUseCaseTest {

    @InjectMocks
    private CriarRestauranteUseCase criarRestauranteUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Test
    @DisplayName("Deve criar um restaurante com sucesso")
    void deveCriarRestauranteComSucesso() {
        // Arrange
        Long usuarioLogadoId = 1L;
        Long idRestauranteGerado = 100L;
        DadosRestauranteInputDTO inputDTO = mock(DadosRestauranteInputDTO.class);

        when(inputDTO.nome()).thenReturn("Restaurante do João");
        when(inputDTO.endereco()).thenReturn("Rua Principal, 123");
        when(inputDTO.tipoCozinha()).thenReturn("Italiana");
        when(inputDTO.horarioFuncionamento()).thenReturn("18h-23h");

        Dono donoMock = mock(Dono.class);
        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(donoMock.getUsuarioId()).thenReturn(usuarioLogadoId);
        when(restauranteGateway.salvar(any(Restaurante.class))).thenReturn(idRestauranteGerado);

        // Act
        Long resultado = criarRestauranteUseCase.criar(usuarioLogadoId, inputDTO);

        // Assert
        assertEquals(idRestauranteGerado, resultado);
        verify(validaSeUsuarioDono).validar(donoMock);
    }

    @Test
    @DisplayName("Deve propagar exceção quando o usuário logado não for Dono")
    void devePropagarExcecaoQuandoNaoForDono() {
        // Arrange
        Long usuarioLogadoId = 1L;
        DadosRestauranteInputDTO inputDTO = mock(DadosRestauranteInputDTO.class);
        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);

        doThrow(new UsuarioNaoDonoException())
                .when(validaSeUsuarioDono).validar(donoMock);

        // Act & Assert
        assertThrows(UsuarioNaoDonoException.class, () ->
                criarRestauranteUseCase.criar(usuarioLogadoId, inputDTO)
        );

        verify(restauranteGateway, never()).salvar(any());
    }
}