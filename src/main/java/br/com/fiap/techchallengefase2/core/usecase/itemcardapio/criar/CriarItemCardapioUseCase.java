package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.criar;

import br.com.fiap.techchallengefase2.core.domain.factory.UsuarioFactory;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.cardapio.consultar.id.BuscarCardapioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriarItemCardapioUseCase implements CriarItemCardapio {

    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final ItemCardapioGateway itemCardapioGateway;
    private final BuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;
    private final ValidaSeUsuarioDono validaSeUsuarioDono;
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Override
    public Long criar(Long usuarioLogadoId, Long cardapioId, DadosItemCardapioInputDTO dados) {
        validarSeUsuarioDonoDoRestaurante(usuarioLogadoId, cardapioId);

        ItemCardapio item = new ItemCardapio(
                null,
                dados.nome(),
                dados.descricao(),
                dados.preco(),
                dados.disponivelApenasRestaurante(),
                dados.caminhoFoto(),
                cardapioId
        );

        return itemCardapioGateway.salvar(item);
    }

    private void validarSeUsuarioDonoDoRestaurante(Long usuarioLogadoId, Long cardapioId) {
        UsuarioBase usuarioBase = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);
        validaSeUsuarioDono.validar(usuarioBase);
        Cardapio cardapio = buscarCardapioPorIdUseCase.buscarPorId(cardapioId);
        Dono dono = UsuarioFactory.obterInstancia(usuarioBase, Dono.class);
        validaSeUsuarioDonoRestaurante.validar(dono, cardapio.getRestaurante().getRestauranteId());
    }
}