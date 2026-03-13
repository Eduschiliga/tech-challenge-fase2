package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.ItemCardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemCardapioGatewayImpl implements ItemCardapioGateway {

    private final ItemCardapioRepository repository;

    @Override
    public Long salvar(ItemCardapio itemCardapio) {
        ItemCardapioEntityJPA entity = ItemCardapioEntityJPA.builder()
                .itemCardapioId(itemCardapio.getItemCardapioId())
                .nome(itemCardapio.getNome())
                .descricao(itemCardapio.getDescricao())
                .preco(itemCardapio.getPreco())
                .disponivelApenasRestaurante(itemCardapio.getDisponivelApenasRestaurante())
                .caminhoFoto(itemCardapio.getCaminhoFoto())
                .restauranteId(itemCardapio.getRestauranteId())
                .build();

        return repository.save(entity).getItemCardapioId();
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(Long itemCardapioId) {
        return repository.findById(itemCardapioId).map(this::toDomain);
    }

    @Override
    public List<ItemCardapio> buscarTodosPorRestauranteId(Long restauranteId) {
        return repository.findAllByRestauranteId(restauranteId).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(Long itemCardapioId) {
        repository.deleteById(itemCardapioId);
    }

    private ItemCardapio toDomain(ItemCardapioEntityJPA entity) {
        return new ItemCardapio(
                entity.getItemCardapioId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getDisponivelApenasRestaurante(),
                entity.getCaminhoFoto(),
                entity.getRestauranteId()
        );
    }
}