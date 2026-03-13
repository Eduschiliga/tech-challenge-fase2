package br.com.fiap.techchallengefase2.infra.config;

import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos.BuscarItensPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos.BuscarItensPorRestauranteUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar.CriarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar.CriarItemCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar.DeletarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar.DeletarItemCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemCardapioConfig {

    @Bean
    public BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            ItemCardapioGateway itemCardapioGateway
    ) {
        return new BuscarItemCardapioPorIdUseCase(buscarUsuarioPorIdUseCase, validaSeUsuarioDono, validaSeUsuarioDonoRestaurante, itemCardapioGateway);
    }

    @Bean
    public BuscarItensPorRestaurante buscarItensPorRestaurante(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            ItemCardapioGateway itemCardapioGateway
    ) {
        return new BuscarItensPorRestauranteUseCase(buscarUsuarioPorIdUseCase, validaSeUsuarioDono, validaSeUsuarioDonoRestaurante, itemCardapioGateway);
    }

    @Bean
    public CriarItemCardapio criarItemCardapio(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ItemCardapioGateway itemCardapioGateway,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante
    ) {
        return new CriarItemCardapioUseCase(buscarUsuarioPorIdUseCase, itemCardapioGateway, validaSeUsuarioDono, validaSeUsuarioDonoRestaurante);
    }

    @Bean
    public AtualizarItemCardapio atualizarItemCardapio(
            BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase,
            ItemCardapioGateway itemCardapioGateway
    ) {
        return new AtualizarItemCardapioUseCase(buscarItemCardapioPorIdUseCase, itemCardapioGateway);
    }

    @Bean
    public DeletarItemCardapio deletarItemCardapio(
            BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase,
            ItemCardapioGateway itemCardapioGateway
    ) {
        return new DeletarItemCardapioUseCase(buscarItemCardapioPorIdUseCase, itemCardapioGateway);
    }
}