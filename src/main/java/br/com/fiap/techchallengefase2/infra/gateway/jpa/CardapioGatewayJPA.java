package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.exception.RestauranteNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.CardapioGateway;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.CardapioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardapioGatewayJPA implements CardapioGateway {

    private final CardapioRepository repository;
    private final CardapioMapperJPA mapper;
    private final RestauranteRepository restauranteRepository;

    @Override
    public Long salvar(Cardapio cardapio) {
        CardapioEntityJPA entity = mapper.toEntity(cardapio);

        RestauranteEntityJPA restaurante = restauranteRepository.findById(cardapio.getRestaurante().getRestauranteId())
                .orElseThrow(RestauranteNaoEncontradoException::new);

        entity.setRestaurante(restaurante);

        return repository.save(entity).getId();
    }

    @Override
    public Optional<Cardapio> buscarPorId(Long id) {
        Optional<CardapioEntityJPA> cardapioEntityJPA = repository.findById(id);

        return cardapioEntityJPA.map(mapper::toDomain);
    }

    @Override
    public List<Cardapio> buscarTodosPorRestauranteId(Long restauranteId) {
        return repository.findAllByRestaurante_RestauranteId(restauranteId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Cardapio> buscarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}
