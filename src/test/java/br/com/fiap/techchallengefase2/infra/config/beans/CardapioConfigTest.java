package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar.AtualizarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante.BuscarTodosCardapiosPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.criar.CriarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar.DeletarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CardapioConfigTest {

    private final CardapioConfig cardapioConfig = new CardapioConfig();
    private final CardapioGateway cardapioGateway = mock(CardapioGateway.class);
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
    private final ValidaSeUsuarioDono validaSeUsuarioDono = mock(ValidaSeUsuarioDono.class);
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante = mock(ValidaSeUsuarioDonoRestaurante.class);
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase = mock(BuscarRestaurantePorIdUseCase.class);
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase = mock(BuscarCardapioPorIdUseCase.class);

    @Test
    void buscarCardapioPorIdUseCase_DeveInstanciarCorretamente() {
        var result = cardapioConfig.buscarCardapioPorIdUseCase(cardapioGateway);
        assertNotNull(result);
    }

    @Test
    void criarCardapio_DeveInstanciarCorretamente() {
        CriarCardapio result = cardapioConfig.criarCardapio(
                buscarUsuarioPorIdUseCase,
                cardapioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                buscarRestaurantePorIdUseCase
        );
        assertNotNull(result);
    }

    @Test
    void atualizarCardapio_DeveInstanciarCorretamente() {
        AtualizarCardapio result = cardapioConfig.atualizarCardapio(
                buscarCardapioPorIdUseCase,
                cardapioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                buscarUsuarioPorIdUseCase
        );
        assertNotNull(result);
    }

    @Test
    void buscarCardapioPorId_DeveRetornarInstancia() {
        BuscarCardapioPorId result = cardapioConfig.buscarCardapioPorId(buscarCardapioPorIdUseCase);
        assertNotNull(result);
    }

    @Test
    void buscarTodosCardapiosPorRestaurante_DeveInstanciarCorretamente() {
        BuscarTodosCardapiosPorRestaurante result = cardapioConfig.buscarTodosCardapiosPorRestaurante(cardapioGateway);
        assertNotNull(result);
    }

    @Test
    void deletarCardapio_DeveInstanciarCorretamente() {
        DeletarCardapio result = cardapioConfig.deletarCardapio(
                buscarCardapioPorIdUseCase,
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDonoRestaurante,
                validaSeUsuarioDono,
                cardapioGateway
        );
        assertNotNull(result);
    }
}