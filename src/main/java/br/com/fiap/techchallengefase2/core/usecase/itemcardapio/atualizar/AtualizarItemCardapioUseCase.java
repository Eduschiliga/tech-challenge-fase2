package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.atualizar;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.exception.CardapioNaoEncontraoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarItemCardapioUseCase implements AtualizarItemCardapio {

    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final ItemCardapioGateway itemCardapioGateway;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Override
    public Long atualizar(Long usuarioLogadoId, Long itemCardapioId, DadosItemCardapioInputDTO dados) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        ItemCardapio item = buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId);
        validarSeUsuarioDonoRestaurante(item, usuarioBase);

        item.atualizarDados(
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                dados.disponivelApenasRestaurante(),
                dados.caminhoFoto()
        );

        return itemCardapioGateway.salvar(item);
    }


    private void validarSeUsuarioDonoRestaurante(ItemCardapio item, UsuarioBase usuarioBase) {
        Cardapio cardapio = buscarCardapioPorIdUseCase.buscarPorId(item.getCardapioId());

        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);

        validaSeUsuarioDonoRestaurante.validar(dono, cardapio.getRestaurante().getRestauranteId());
    }
}