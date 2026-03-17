package br.com.fiap.techchallengefase2.core.usecase.cardapio.atualizar;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import static br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioFactory.obterInstancia;

@RequiredArgsConstructor
public class AtualizarCardapioUseCase implements AtualizarCardapio {
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;
    private final CardapioGateway cardapioGateway;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Override
    public Long atualizar(Long usuarioLogadoId, AtualizarCardapioInputDTO input) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);

        Cardapio cardapio = buscarCardapioPorIdUseCase.buscarPorId(input.cardapioId());
        Dono dono = obterInstancia(usuarioBase, Dono.class);
        validaSeUsuarioDonoRestaurante.validar(dono, cardapio.getRestaurante().getRestauranteId());

        cardapio.atualizarNome(input.nome());

        return cardapioGateway.salvar(cardapio);
    }

}