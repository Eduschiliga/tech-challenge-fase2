package br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletarCardapioUseCase implements DeletarCardapio {

    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final CardapioGateway cardapioGateway;

    @Override
    public void deletarPorId(Long usuarioLogadoId, Long itemCardapioId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        Cardapio cardapio = buscarCardapioPorIdUseCase.buscarPorId(itemCardapioId);
        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);

        validaSeUsuarioDonoRestaurante.validar(dono, cardapio.getRestaurante().getRestauranteId());

        cardapioGateway.deletarPorId(cardapio.getId());
    }

}
