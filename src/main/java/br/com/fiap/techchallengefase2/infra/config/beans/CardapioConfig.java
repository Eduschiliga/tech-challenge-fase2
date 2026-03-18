package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar.AtualizarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar.AtualizarCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante.BuscarTodosCardapiosPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante.BuscarTodosCardapiosUseCase;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.criar.CriarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.criar.CriarItemCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar.DeletarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar.DeletarCardapioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardapioConfig {

    @Bean
    public BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase(CardapioGateway cardapioGateway) {
        return new BuscarCardapioPorIdUseCase(cardapioGateway);
    }

    @Bean
    public CriarCardapio criarCardapio(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            CardapioGateway cardapioGateway,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase) {
        return new CriarItemCardapioUseCase(
                buscarUsuarioPorIdUseCase,
                cardapioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                buscarRestaurantePorIdUseCase
        );
    }

    @Bean
    public AtualizarCardapio atualizarCardapio(
            BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase,
            CardapioGateway cardapioGateway,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase) {
        return new AtualizarCardapioUseCase(
                buscarCardapioPorIdUseCase,
                cardapioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                buscarUsuarioPorIdUseCase
        );
    }

    @Bean
    public BuscarCardapioPorId buscarCardapioPorId(BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase) {
        return buscarCardapioPorIdUseCase;
    }

    @Bean
    public BuscarTodosCardapiosPorRestaurante buscarTodosCardapiosPorRestaurante(CardapioGateway cardapioGateway) {
        return new BuscarTodosCardapiosUseCase(cardapioGateway);
    }

    @Bean
    public DeletarCardapio deletarCardapio(
            BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            CardapioGateway cardapioGateway) {
        return new DeletarCardapioUseCase(
                buscarCardapioPorIdUseCase,
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDonoRestaurante,
                validaSeUsuarioDono,
                cardapioGateway
        );
    }
}
