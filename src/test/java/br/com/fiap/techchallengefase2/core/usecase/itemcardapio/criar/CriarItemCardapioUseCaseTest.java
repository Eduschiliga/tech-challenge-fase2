package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
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
class CriarItemCardapioUseCaseTest {

    @InjectMocks
    private CriarItemCardapioUseCase criarItemCardapioUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Test
    void deveCriarItemCardapioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        Long itemGeradoId = 100L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Prato", "Descricao", 50.0, true, "/foto.png");

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(itemCardapioGateway.salvar(any(ItemCardapio.class))).thenReturn(itemGeradoId);

        Long resultado = criarItemCardapioUseCase.criar(usuarioLogadoId, restauranteId, dados);

        assertEquals(itemGeradoId, resultado);
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
        verify(itemCardapioGateway).salvar(any(ItemCardapio.class));
    }

    @Test
    void devePropagarExcecaoQuandoUsuarioNaoForDono() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Prato", "Desc", 50.0, true, "/foto.png");

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDono).validar(donoMock);

        assertThrows(IllegalArgumentException.class, () ->
                criarItemCardapioUseCase.criar(usuarioLogadoId, restauranteId, dados)
        );

        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    void devePropagarExcecaoQuandoNaoForDonoDoRestaurante() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Prato", "Desc", 50.0, true, "/foto.png");

        Dono donoMock = mock(Dono.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(IllegalArgumentException.class).when(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);

        assertThrows(IllegalArgumentException.class, () ->
                criarItemCardapioUseCase.criar(usuarioLogadoId, restauranteId, dados)
        );

        verify(itemCardapioGateway, never()).salvar(any());
    }
}