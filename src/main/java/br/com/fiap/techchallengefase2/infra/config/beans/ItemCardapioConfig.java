package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorId;
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
            ItemCardapioGateway itemCardapioGateway
    ) {
        return new BuscarItemCardapioPorIdUseCase(itemCardapioGateway);
    }

    @Bean
    public BuscarItemCardapioPorId buscarItemCardapioPorId(BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase) {
        return buscarItemCardapioPorIdUseCase;
    }

    @Bean
    public BuscarItensPorRestaurante buscarItensPorRestaurante(
            ItemCardapioGateway itemCardapioGateway
    ) {
        return new BuscarItensPorRestauranteUseCase(itemCardapioGateway);
    }

    @Bean
    public CriarItemCardapio criarItemCardapio(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ItemCardapioGateway itemCardapioGateway,
            BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante
    ) {
        return new CriarItemCardapioUseCase(
                buscarUsuarioPorIdUseCase,
                itemCardapioGateway,
                buscarCardapioPorIdUseCase,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante
        );
    }

    @Bean
    public AtualizarItemCardapio atualizarItemCardapio(
            BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase,
            ItemCardapioGateway itemCardapioGateway,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase
    ) {
        return new AtualizarItemCardapioUseCase(
                buscarItemCardapioPorIdUseCase,
                itemCardapioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                buscarUsuarioPorIdUseCase,
                buscarCardapioPorIdUseCase
        );
    }

    @Bean
    public DeletarItemCardapio deletarItemCardapio(
            BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase,
            ItemCardapioGateway itemCardapioGateway,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase
    ) {
        return new DeletarItemCardapioUseCase(
                buscarItemCardapioPorIdUseCase,
                itemCardapioGateway,
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDonoRestaurante,
                validaSeUsuarioDono,
                buscarCardapioPorIdUseCase
        );
    }
}
