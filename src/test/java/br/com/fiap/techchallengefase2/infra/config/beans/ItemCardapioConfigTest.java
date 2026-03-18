package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos.BuscarItensPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar.CriarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar.DeletarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ItemCardapioConfigTest {

    private final ItemCardapioConfig itemCardapioConfig = new ItemCardapioConfig();
    private final ItemCardapioGateway itemCardapioGateway = mock(ItemCardapioGateway.class);
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase = mock(BuscarCardapioPorIdUseCase.class);
    private final ValidaSeUsuarioDono validaSeUsuarioDono = mock(ValidaSeUsuarioDono.class);
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante = mock(ValidaSeUsuarioDonoRestaurante.class);
    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase = mock(BuscarItemCardapioPorIdUseCase.class);

    @Test
    void buscarItemCardapioPorIdUseCase_DeveInstanciarCorretamente() {
        var result = itemCardapioConfig.buscarItemCardapioPorIdUseCase(itemCardapioGateway);
        assertNotNull(result);
    }

    @Test
    void buscarItemCardapioPorId_DeveRetornarInstancia() {
        BuscarItemCardapioPorId result = itemCardapioConfig.buscarItemCardapioPorId(buscarItemCardapioPorIdUseCase);
        assertNotNull(result);
    }

    @Test
    void buscarItensPorRestaurante_DeveInstanciarCorretamente() {
        BuscarItensPorRestaurante result = itemCardapioConfig.buscarItensPorRestaurante(itemCardapioGateway);
        assertNotNull(result);
    }

    @Test
    void criarItemCardapio_DeveInstanciarCorretamente() {
        CriarItemCardapio result = itemCardapioConfig.criarItemCardapio(
                buscarUsuarioPorIdUseCase,
                itemCardapioGateway,
                buscarCardapioPorIdUseCase,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante
        );
        assertNotNull(result);
    }

    @Test
    void atualizarItemCardapio_DeveInstanciarCorretamente() {
        AtualizarItemCardapio result = itemCardapioConfig.atualizarItemCardapio(
                buscarItemCardapioPorIdUseCase,
                itemCardapioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                buscarUsuarioPorIdUseCase,
                buscarCardapioPorIdUseCase
        );
        assertNotNull(result);
    }

    @Test
    void deletarItemCardapio_DeveInstanciarCorretamente() {
        DeletarItemCardapio result = itemCardapioConfig.deletarItemCardapio(
                buscarItemCardapioPorIdUseCase,
                itemCardapioGateway,
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDonoRestaurante,
                validaSeUsuarioDono,
                buscarCardapioPorIdUseCase
        );
        assertNotNull(result);
    }
}