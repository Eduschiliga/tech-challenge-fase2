package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.exception.UsuarioNaoDonoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

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
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Test
    void deveCriarItemCardapioComSucesso() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        Long restauranteId = 20L;
        Long itemGeradoId = 100L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Prato", "Descricao", 50.0, true, "/foto.png");

        Dono donoMock = new Dono(usuarioLogadoId, "Nome", "Email", "Login", "Senha", "Endereco", new ArrayList<>());
        Cardapio cardapioMock = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarCardapioPorIdUseCase.buscarPorId(cardapioId)).thenReturn(cardapioMock);
        when(cardapioMock.getRestaurante()).thenReturn(restauranteMock);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);
        when(itemCardapioGateway.salvar(any(ItemCardapio.class))).thenReturn(itemGeradoId);

        Long resultado = criarItemCardapioUseCase.criar(usuarioLogadoId, cardapioId, dados);

        assertEquals(itemGeradoId, resultado);
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(any(), eq(restauranteId));
        verify(itemCardapioGateway).salvar(any(ItemCardapio.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForDono() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Prato", "Desc", 50.0, true, "/foto.png");

        Dono donoMock = new Dono(usuarioLogadoId, "Nome", "Email", "Login", "Senha", "Endereco", new ArrayList<>());

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        doThrow(UsuarioNaoDonoException.class).when(validaSeUsuarioDono).validar(donoMock);

        assertThrows(UsuarioNaoDonoException.class, () ->
                criarItemCardapioUseCase.criar(usuarioLogadoId, cardapioId, dados)
        );

        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForDonoDoRestaurante() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        Long restauranteId = 20L;
        DadosItemCardapioInputDTO dados = new DadosItemCardapioInputDTO("Prato", "Desc", 50.0, true, "/foto.png");

        Dono donoMock = new Dono(usuarioLogadoId, "Nome", "Email", "Login", "Senha", "Endereco", new ArrayList<>());
        Cardapio cardapioMock = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarCardapioPorIdUseCase.buscarPorId(cardapioId)).thenReturn(cardapioMock);
        when(cardapioMock.getRestaurante()).thenReturn(restauranteMock);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);
        doThrow(UsuarioNaoDonoException.class).when(validaSeUsuarioDonoRestaurante).validar(any(), eq(restauranteId));

        assertThrows(UsuarioNaoDonoException.class, () ->
                criarItemCardapioUseCase.criar(usuarioLogadoId, cardapioId, dados)
        );

        verify(itemCardapioGateway, never()).salvar(any());
    }
}
