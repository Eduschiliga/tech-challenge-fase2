package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.itemcardapio.consultar.id.BuscarItemCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletarItemCardapioUseCase implements DeletarItemCardapio {

    private final BuscarItemCardapioPorIdUseCase buscarItemCardapioPorIdUseCase;
    private final ItemCardapioGateway itemCardapioGateway;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;

    @Override
    public void deletarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        validarSeUsuarioDonoRestaurante(usuarioLogadoId, itemCardapioId);

        itemCardapioGateway.deletarPorId(itemCardapioId);
    }

    private void validarSeUsuarioDonoRestaurante(Long usuarioLogadoId, Long itemCardapioId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        ItemCardapio item = buscarItemCardapioPorIdUseCase.buscarPorId(usuarioLogadoId, itemCardapioId);
        Cardapio cardapio = buscarCardapioPorIdUseCase.buscarPorId(item.getCardapioId());

        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);

        validaSeUsuarioDonoRestaurante.validar(dono, cardapio.getRestaurante().getRestauranteId());
    }
}
