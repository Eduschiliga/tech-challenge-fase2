package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardapioGatewayImplTest {

    @InjectMocks
    private CardapioGatewayImpl cardapioGateway;

    @Mock
    private CardapioRepository repository;

    @Test
    void deveSalvarCardapioComSucesso() {
        Restaurante restaurante = new Restaurante(10L, "Rest", "End", "Tipo", "Horario", 1L);
        Cardapio cardapio = new Cardapio(1L, restaurante, null, "Menu Principal");
        CardapioEntityJPA entitySalva = CardapioEntityJPA.builder().id(1L).build();

        when(repository.save(any(CardapioEntityJPA.class))).thenReturn(entitySalva);

        Long idSalvo = cardapioGateway.salvar(cardapio);

        assertEquals(1L, idSalvo);
        verify(repository).save(any(CardapioEntityJPA.class));
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        CardapioEntityJPA entity = CardapioEntityJPA.builder()
                .id(1L)
                .nome("Menu")
                .restaurante(RestauranteEntityJPA.builder().restauranteId(10L).build())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Cardapio> resultado = cardapioGateway.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(entity.getId(), resultado.get().getId());
        assertEquals(entity.getNome(), resultado.get().getNome());
    }

    @Test
    void deveBuscarTodosPorRestauranteId() {
        CardapioEntityJPA entity1 = CardapioEntityJPA.builder().id(1L).restaurante(RestauranteEntityJPA.builder().restauranteId(10L).build()).build();
        CardapioEntityJPA entity2 = CardapioEntityJPA.builder().id(2L).restaurante(RestauranteEntityJPA.builder().restauranteId(10L).build()).build();

        when(repository.findAllByRestaurante_RestauranteId(10L)).thenReturn(List.of(entity1, entity2));

        List<Cardapio> resultados = cardapioGateway.buscarTodosPorRestauranteId(10L);

        assertEquals(2, resultados.size());
        assertEquals(1L, resultados.get(0).getId());
        assertEquals(2L, resultados.get(1).getId());
    }

    @Test
    void deveDeletarPorIdComSucesso() {
        cardapioGateway.deletarPorId(1L);
        verify(repository).deleteById(1L);
    }
}
