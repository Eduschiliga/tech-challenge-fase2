package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar.AtualizarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorId;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.todos.BuscarItensPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar.CriarItemCardapio;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar.DeletarItemCardapio;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ItemCardapioController {

    private final CriarItemCardapio criarItemCardapio;
    private final AtualizarItemCardapio atualizarItemCardapio;
    private final BuscarItemCardapioPorId buscarItemCardapioPorId;
    private final BuscarItensPorRestaurante buscarItensPorRestaurante;
    private final DeletarItemCardapio deletarItemCardapio;

    public Long criar(Long usuarioLogadoId, Long cardapioId, DadosItemCardapioInputDTO dados) {
        return criarItemCardapio.criar(usuarioLogadoId, cardapioId, dados);
    }

    public Long atualizar(Long usuarioLogadoId, Long itemCardapioId, DadosItemCardapioInputDTO dados) {
        return atualizarItemCardapio.atualizar(usuarioLogadoId, itemCardapioId, dados);
    }

    public ItemCardapioOutputDTO buscarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        ItemCardapio item = buscarItemCardapioPorId.buscarPorId(usuarioLogadoId, itemCardapioId);
        return ItemCardapioOutputDTO.fromDomain(item);
    }

    public List<ItemCardapioOutputDTO> buscarTodosPorCardapio(Long usuarioLogadoId, Long cardapioId) {
        return buscarItensPorRestaurante.buscarTodos(usuarioLogadoId, cardapioId)
                .stream()
                .map(ItemCardapioOutputDTO::fromDomain)
                .collect(Collectors.toList());
    }

    public void deletarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        deletarItemCardapio.deletarPorId(usuarioLogadoId, itemCardapioId);
    }
}
