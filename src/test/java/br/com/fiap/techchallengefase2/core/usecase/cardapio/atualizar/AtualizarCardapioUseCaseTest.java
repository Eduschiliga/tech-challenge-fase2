package br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarCardapioUseCaseTest {

    @Mock
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Mock
    private CardapioGateway cardapioGateway;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @InjectMocks
    private AtualizarCardapioUseCase atualizarCardapioUseCase;

    @Test
    void atualizar_DeveAtualizarNomeESalvarComSucesso() {
        Long usuarioLogadoId = 1L;
        Long cardapioId = 10L;
        Long restauranteId = 100L;
        AtualizarCardapioInputDTO input = new AtualizarCardapioInputDTO(cardapioId, "Novo Nome Cardápio");

        Dono dono = mock(Dono.class);
        Cardapio cardapio = mock(Cardapio.class);
        Restaurante restaurante = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(dono);
        when(buscarCardapioPorIdUseCase.buscarPorId(cardapioId)).thenReturn(cardapio);
        when(cardapio.getRestaurante()).thenReturn(restaurante);
        when(restaurante.getRestauranteId()).thenReturn(restauranteId);
        when(cardapioGateway.salvar(cardapio)).thenReturn(cardapioId);

        Long resultId = atualizarCardapioUseCase.atualizar(usuarioLogadoId, input);

        assertEquals(cardapioId, resultId);
        verify(validaSeUsuarioDono).validar(dono);
        verify(validaSeUsuarioDonoRestaurante).validar(dono, restauranteId);
        verify(cardapio).atualizarNome("Novo Nome Cardápio");
        verify(cardapioGateway).salvar(cardapio);
    }
}