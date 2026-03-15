package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.ItemCardapioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.CardapioNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemCardapioGatewayJPA implements ItemCardapioGateway {

    private final ItemCardapioRepository repository;
    private final CardapioRepository cardapioRepository;
    private final ItemCardapioMapperJPA mapper;

    @Override
    public Long salvar(ItemCardapio itemCardapio) {
        ItemCardapioEntityJPA entity = mapper.toEntity(itemCardapio);

        CardapioEntityJPA cardapio = cardapioRepository.findById(itemCardapio.getCardapioId())
                .orElseThrow(CardapioNaoEncontradoException::new);

        entity.setCardapio(cardapio);

        return repository.save(entity).getItemCardapioId();
    }

    @Override
    public Optional<ItemCardapio> buscarPorId(Long itemCardapioId) {
        return repository.findById(itemCardapioId).map(mapper::toDomain);
    }

    @Override
    public List<ItemCardapio> buscarTodosPorCardapioId(Long cardapioId) {
        return repository.findAllByCardapio_Id(cardapioId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(Long itemCardapioId) {
        repository.deleteById(itemCardapioId);
    }
}
