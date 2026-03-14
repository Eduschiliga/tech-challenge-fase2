package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.ItemCardapioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarItemCardapioPorIdUseCase implements BuscarItemCardapioPorId {

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    private final ItemCardapioGateway itemCardapioGateway;

    @Override
    public ItemCardapio buscarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        ItemCardapio item = itemCardapioGateway.buscarPorId(itemCardapioId)
                .orElseThrow(ItemCardapioNaoEncontradoException::new);

        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);
        validaSeUsuarioDonoRestaurante.validar(dono, item.getRestauranteId());

        return item;
    }
}