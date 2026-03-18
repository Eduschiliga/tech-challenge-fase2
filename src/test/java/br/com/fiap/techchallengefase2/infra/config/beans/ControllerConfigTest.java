package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.controller.*;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar.AtualizarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante.BuscarTodosCardapiosPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.criar.CriarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar.DeletarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos.BuscarItensPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar.CriarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar.DeletarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.atualizar.AtualizarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.idusuario.BuscarRestaurantePorUsuarioId;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.todos.BuscarTodosRestaurantes;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.criar.CriarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.deletar.DeletarRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario.BuscarTipoUsuarioPorUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados.AtualizarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha.AtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorId;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.todos.BuscarTodosUsuarios;
import br.com.fiap.techchallengefase2.core.usecase.usuario.criar.CriarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.deletar.DeletarUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.atribuir.AtribuirTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.tipousuario.remover.RemoverTipoUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ControllerConfigTest {

    @InjectMocks
    private ControllerConfig controllerConfig;

    @Mock
    private DeletarUsuario deletarUsuario;
    @Mock
    private CriarUsuario criarUsuario;
    @Mock
    private BuscarUsuarioPorId buscarUsuarioPorId;
    @Mock
    private BuscarTodosUsuarios buscarTodosUsuarios;
    @Mock
    private AtualizarSenhaUsuario atualizarSenhaUsuario;
    @Mock
    private AtualizarUsuario atualizarUsuario;
    @Mock
    private AtribuirTipoUsuario atribuirTipoUsuario;
    @Mock
    private RemoverTipoUsuario removerTipoUsuario;

    @Mock
    private CriarItemCardapio criarItemCardapio;
    @Mock
    private AtualizarItemCardapio atualizarItemCardapio;
    @Mock
    private BuscarItemCardapioPorId buscarItemCardapioPorId;
    @Mock
    private BuscarItensPorRestaurante buscarItensPorRestaurante;
    @Mock
    private DeletarItemCardapio deletarItemCardapio;

    @Mock
    private CriarCardapio criarCardapio;
    @Mock
    private AtualizarCardapio atualizarCardapio;
    @Mock
    private BuscarCardapioPorId buscarCardapioPorId;
    @Mock
    private BuscarTodosCardapiosPorRestaurante buscarTodosCardapiosPorRestaurante;
    @Mock
    private DeletarCardapio deletarCardapio;

    @Mock
    private CriarRestaurante criarRestaurante;
    @Mock
    private AtualizarRestaurante atualizarRestaurante;
    @Mock
    private BuscarRestaurantePorId buscarRestaurantePorId;
    @Mock
    private DeletarRestaurante deletarRestaurante;
    @Mock
    private BuscarRestaurantePorUsuarioId buscarRestaurantePorUsuarioId;
    @Mock
    private BuscarTodosRestaurantes buscarTodosRestaurantes;

    @Mock
    private CriarTipoUsuario criarTipoUsuario;
    @Mock
    private AtualizarTipoUsuario atualizarTipoUsuario;
    @Mock
    private BuscarTipoUsuarioPorId buscarTipoUsuarioPorId;
    @Mock
    private DeletarTipoUsuario deletarTipoUsuario;
    @Mock
    private BuscarTipoUsuarioPorRestaurante buscarTipoUsuarioPorRestaurante;
    @Mock
    private BuscarTipoUsuarioPorUsuario buscarTipoUsuarioPorUsuario;

    @Test
    void usuarioController_DeveInstanciarCorretamente() {
        UsuarioController bean = controllerConfig.usuarioController(
                deletarUsuario, criarUsuario, buscarUsuarioPorId, buscarTodosUsuarios,
                atualizarSenhaUsuario, atualizarUsuario, atribuirTipoUsuario, removerTipoUsuario
        );
        assertNotNull(bean);
    }

    @Test
    void itemCardapioController_DeveInstanciarCorretamente() {
        ItemCardapioController bean = controllerConfig.itemCardapioController(
                criarItemCardapio, atualizarItemCardapio, buscarItemCardapioPorId,
                buscarItensPorRestaurante, deletarItemCardapio
        );
        assertNotNull(bean);
    }

    @Test
    void cardapioController_DeveInstanciarCorretamente() {
        CardapioController bean = controllerConfig.cardapioController(
                criarCardapio, atualizarCardapio, buscarCardapioPorId,
                buscarTodosCardapiosPorRestaurante, deletarCardapio
        );
        assertNotNull(bean);
    }

    @Test
    void restauranteController_DeveInstanciarCorretamente() {
        RestauranteController bean = controllerConfig.restauranteController(
                criarRestaurante, atualizarRestaurante, buscarRestaurantePorId,
                deletarRestaurante, buscarRestaurantePorUsuarioId, buscarTodosRestaurantes
        );
        assertNotNull(bean);
    }

    @Test
    void tipoUsuarioController_DeveInstanciarCorretamente() {
        TipoUsuarioController bean = controllerConfig.tipoUsuarioController(
                criarTipoUsuario, atualizarTipoUsuario, buscarTipoUsuarioPorId,
                deletarTipoUsuario, buscarTipoUsuarioPorRestaurante, buscarTipoUsuarioPorUsuario
        );
        assertNotNull(bean);
    }
}