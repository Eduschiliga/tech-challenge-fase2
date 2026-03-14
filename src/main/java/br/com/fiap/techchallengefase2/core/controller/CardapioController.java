package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar.AtualizarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.todosporrestaurante.BuscarTodosCardapiosPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.criar.CriarCardapio;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar.DeletarCardapio;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardapioController {

    private final CriarCardapio criarCardapio;
    private final AtualizarCardapio atualizarCardapio;
    private final BuscarCardapioPorId buscarCardapioPorId;
    private final BuscarTodosCardapiosPorRestaurante buscarTodosCardapiosPorRestaurante;
    private final DeletarCardapio deletarCardapio;

    public Long criar(Long usuarioLogadoId, CriarCardapioInputDTO dados) {
        return criarCardapio.criar(usuarioLogadoId, dados);
    }

    public Long atualizar(Long usuarioLogadoId, AtualizarCardapioInputDTO dados) {
        return atualizarCardapio.atualizar(usuarioLogadoId, dados);
    }

    public CardapioOutputDTO buscarPorId(Long cardapioId) {
        Cardapio cardapio = buscarCardapioPorId.buscarPorId(cardapioId);
        return CardapioOutputDTO.fromDomain(cardapio);
    }

    public List<CardapioOutputDTO> buscarTodosPorRestaurante(Long usuarioLogadoId, Long restauranteId) {
        return buscarTodosCardapiosPorRestaurante.buscarTodos(usuarioLogadoId, restauranteId)
                .stream()
                .map(CardapioOutputDTO::fromDomain)
                .collect(Collectors.toList());
    }

    public void deletarPorId(Long usuarioLogadoId, Long cardapioId) {
        deletarCardapio.deletarPorId(usuarioLogadoId, cardapioId);
    }
}
