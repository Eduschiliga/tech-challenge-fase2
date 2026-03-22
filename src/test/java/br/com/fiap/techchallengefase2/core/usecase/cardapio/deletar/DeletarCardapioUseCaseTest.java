package br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
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

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarCardapioUseCaseTest {

    @Mock
    private BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private CardapioGateway cardapioGateway;

    @InjectMocks
    private DeletarCardapioUseCase deletarCardapioUseCase;

    @Test
    void deletarPorId_DeveValidarDonoERestauranteAntesDeDeletar() {
        Long usuarioLogadoId = 1L;
        Long itemCardapioId = 10L;
        Long restauranteId = 100L;

        Dono donoMock = mock(Dono.class);
        Cardapio cardapioMock = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarCardapioPorIdUseCase.buscarPorId(itemCardapioId)).thenReturn(cardapioMock);
        when(cardapioMock.getRestaurante()).thenReturn(restauranteMock);
        when(restauranteMock.getRestauranteId()).thenReturn(restauranteId);
        when(cardapioMock.getCardapioId()).thenReturn(itemCardapioId);

        deletarCardapioUseCase.deletarPorId(usuarioLogadoId, itemCardapioId);

        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(argThat(d -> d.equals(donoMock)), eq(restauranteId));
        verify(cardapioGateway).deletarPorId(itemCardapioId);
    }
}