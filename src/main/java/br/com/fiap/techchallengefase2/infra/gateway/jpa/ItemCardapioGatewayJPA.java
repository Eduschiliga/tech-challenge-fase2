package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.gateway.ItemCardapioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.ItemCardapioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.cardapio.CardapioNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemCardapioGatewayJPA implements ItemCardapioGateway {

    private final ItemCardapioRepository repository;
    private final CardapioRepository cardapioRepository;
    private final ItemCardapioMapperJPA mapper;

    @Override
    @Transactional
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
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public void deletarPorId(Long itemCardapioId) {
        Optional<ItemCardapioEntityJPA> itemOpt = repository.findById(itemCardapioId);

        if (itemOpt.isPresent()) {
            ItemCardapioEntityJPA item = itemOpt.get();
            CardapioEntityJPA cardapio = item.getCardapio();
            
            if (cardapio != null && cardapio.getItens() != null) {
                cardapio.getItens().remove(item);
            }

            repository.delete(item);
        }
    }
}
