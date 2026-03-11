package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarItemCardapioUseCase implements AtualizarItemCardapio {

    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final ItemCardapioGateway itemCardapioGateway;

    @Override
    public Long atualizar(Long usuarioLogadoId, Long itemCardapioId, DadosItemCardapioInputDTO dados) {
        ItemCardapio item = buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId);

        item.atualizarDados(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                dados.disponivelApenasRestaurante(),
                dados.caminhoFoto()
        );

        return itemCardapioGateway.salvar(item);
    }
}