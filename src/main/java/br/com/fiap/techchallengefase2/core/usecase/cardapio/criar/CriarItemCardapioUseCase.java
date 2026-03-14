package br.com.fiap.techchallengefase2.core.usecase.cardapio.criar;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.restaurante.consultar.id.BuscarRestaurantePorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class CriarItemCardapioUseCase implements CriarCardapio {

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final CardapioGateway cardapioGateway;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    private final BuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;

    @Override
    public Long criar(Long usuarioLogadoId, CriarCardapioInputDTO input) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);
        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);
        validaSeUsuarioDonoRestaurante.validar(dono, input.restauranteId());

        Restaurante restaurante = buscarRestaurantePorIdUseCase.buscarPorId(
                usuarioLogadoId,
                input.restauranteId()
        );

        Cardapio cardapio = new Cardapio(
                null,
                restaurante,
                new ArrayList<>(),
                input.nome()
        );

        return cardapioGateway.salvar(cardapio);
    }
}