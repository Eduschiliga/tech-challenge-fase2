package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardapioGatewayImpl implements CardapioGateway {

    private final CardapioRepository repository;

    @Override
    public Long salvar(Cardapio cardapio) {
        CardapioEntityJPA entity = CardapioEntityJPA.builder()
                .id(cardapio.getId())
                .nome(cardapio.getNome())
                .restaurante(RestauranteEntityJPA.builder().restauranteId(cardapio.getRestaurante().getRestauranteId()).build())
                .build();

        return repository.save(entity).getId();
    }

    @Override
    public Optional<Cardapio> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Cardapio> buscarTodosPorRestauranteId(Long restauranteId) {
        return repository.findAllByRestaurante_RestauranteId(restauranteId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Cardapio> buscarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }

    private Cardapio toDomain(CardapioEntityJPA entity) {
        Restaurante restaurante = null;
        if (entity.getRestaurante() != null) {
            restaurante = new Restaurante(
                    entity.getRestaurante().getRestauranteId(),
                    entity.getRestaurante().getNome(),
                    entity.getRestaurante().getEndereco(),
                    entity.getRestaurante().getTipoCozinha(),
                    entity.getRestaurante().getHorarioFuncionamento(),
                    entity.getRestaurante().getUsuario() != null ? entity.getRestaurante().getUsuario().getUsuarioId() : null
            );
        }

        List<ItemCardapio> itens = null;
        if (entity.getItens() != null) {
            itens = entity.getItens().stream().map(i -> new ItemCardapio(
                    i.getItemCardapioId(),
                    i.getNome(),
                    i.getDescricao(),
                    i.getPreco(),
                    i.getDisponivelApenasRestaurante(),
                    i.getCaminhoFoto(),
                    entity.getId()
            )).toList();
        }

        return new Cardapio(
                entity.getId(),
                restaurante,
                itens,
                entity.getNome()
        );
    }
}