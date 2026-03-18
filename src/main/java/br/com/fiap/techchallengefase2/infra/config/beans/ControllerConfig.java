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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public UsuarioController usuarioController(
            DeletarUsuario deletarUsuario,
            CriarUsuario criarUsuario,
            BuscarUsuarioPorId buscarUsuarioPorId,
            BuscarTodosUsuarios buscarTodosUsuarios,
            AtualizarSenhaUsuario atualizarSenhaUsuario,
            AtualizarUsuario atualizarUsuario,
            AtribuirTipoUsuario atribuirTipoUsuario,
            RemoverTipoUsuario removerTipoUsuario
    ) {
        return new UsuarioController(
                deletarUsuario,
                criarUsuario,
                buscarUsuarioPorId,
                buscarTodosUsuarios,
                atualizarSenhaUsuario,
                atualizarUsuario,
                atribuirTipoUsuario,
                removerTipoUsuario
        );
    }

    @Bean
    public ItemCardapioController itemCardapioController(
            CriarItemCardapio criarItemCardapio,
            AtualizarItemCardapio atualizarItemCardapio,
            BuscarItemCardapioPorId buscarItemCardapioPorId,
            BuscarItensPorRestaurante buscarItensPorRestaurante,
            DeletarItemCardapio deletarItemCardapio
    ) {
        return new ItemCardapioController(
                criarItemCardapio,
                atualizarItemCardapio,
                buscarItemCardapioPorId,
                buscarItensPorRestaurante,
                deletarItemCardapio
        );
    }

    @Bean
    public CardapioController cardapioController(
            CriarCardapio criarCardapio,
            AtualizarCardapio atualizarCardapio,
            BuscarCardapioPorId buscarCardapioPorId,
            BuscarTodosCardapiosPorRestaurante buscarTodosCardapiosPorRestaurante,
            DeletarCardapio deletarCardapio) {
        return new CardapioController(
                criarCardapio,
                atualizarCardapio,
                buscarCardapioPorId,
                buscarTodosCardapiosPorRestaurante,
                deletarCardapio
        );
    }

    @Bean
    public RestauranteController restauranteController(
            CriarRestaurante criarRestaurante,
            AtualizarRestaurante atualizarRestaurante,
            BuscarRestaurantePorId buscarRestaurantePorId,
            DeletarRestaurante deletarRestaurante,
            BuscarRestaurantePorUsuarioId buscarRestaurantePorUsuarioId,
            BuscarTodosRestaurantes buscarTodosRestaurantes
    ) {
        return new RestauranteController(
                criarRestaurante,
                atualizarRestaurante,
                buscarRestaurantePorId,
                deletarRestaurante,
                buscarRestaurantePorUsuarioId,
                buscarTodosRestaurantes
        );
    }

    @Bean
    public TipoUsuarioController tipoUsuarioController(
            CriarTipoUsuario criarTipoUsuario,
            AtualizarTipoUsuario atualizarTipoUsuario,
            BuscarTipoUsuarioPorId buscarTipoUsuarioPorId,
            DeletarTipoUsuario deletarTipoUsuario,
            BuscarTipoUsuarioPorRestaurante buscarTipoUsuarioPorRestaurante,
            BuscarTipoUsuarioPorUsuario buscarTipoUsuarioPorUsuario
    ) {
        return new TipoUsuarioController(
                criarTipoUsuario,
                atualizarTipoUsuario,
                buscarTipoUsuarioPorId,
                deletarTipoUsuario,
                buscarTipoUsuarioPorRestaurante,
                buscarTipoUsuarioPorUsuario
        );
    }
}
