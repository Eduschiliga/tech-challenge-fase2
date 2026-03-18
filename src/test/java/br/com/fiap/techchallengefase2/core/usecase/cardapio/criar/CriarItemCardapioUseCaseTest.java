package br.com.fiap.techchallengefase2.core.usecase.cardapio.criar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarItemCardapioUseCaseTest {

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private CardapioGateway cardapioGateway;

    @Mock
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Mock
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @InjectMocks
    private CriarItemCardapioUseCase criarItemCardapioUseCase;

    @Test
    void criar_DeveValidarDonoEInstanciarCardapioCorretamente() {
        Long usuarioLogadoId = 1L;
        Long restauranteId = 10L;
        String nomeCardapio = "Cardápio Executivo";
        CriarCardapioInputDTO input = new CriarCardapioInputDTO(restauranteId, nomeCardapio);

        Dono donoMock = mock(Dono.class);
        Restaurante restauranteMock = mock(Restaurante.class);

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId)).thenReturn(donoMock);
        when(buscarRestaurantePorIdUseCase.buscarPorId(usuarioLogadoId, restauranteId)).thenReturn(restauranteMock);
        when(cardapioGateway.salvar(argThat(cardapio ->
                cardapio.getNome().equals(nomeCardapio) &&
                        cardapio.getRestaurante().equals(restauranteMock)
        ))).thenReturn(100L);

        Long idGerado = criarItemCardapioUseCase.criar(usuarioLogadoId, input);

        assertEquals(100L, idGerado);
        verify(validaSeUsuarioDono).validar(donoMock);
        verify(validaSeUsuarioDonoRestaurante).validar(donoMock, restauranteId);
        verify(cardapioGateway).salvar(argThat(cardapio -> cardapio.getNome().equals(nomeCardapio)));
    }
}