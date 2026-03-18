package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.exception.restaurante.RestauranteNaoEncontradoException;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.CardapioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioGatewayJPATest {

    @Mock
    private CardapioRepository repository;

    @Mock
    private CardapioMapperJPA mapper;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private CardapioGatewayJPA gateway;

    @Test
    void deveSalvarCardapioComSucesso() {
        Cardapio cardapio = mock(Cardapio.class);
        Restaurante restauranteDomain = mock(Restaurante.class);
        when(cardapio.getRestaurante()).thenReturn(restauranteDomain);
        when(restauranteDomain.getRestauranteId()).thenReturn(1L);

        CardapioEntityJPA entity = new CardapioEntityJPA();
        RestauranteEntityJPA restauranteEntity = new RestauranteEntityJPA();

        CardapioEntityJPA savedEntity = mock(CardapioEntityJPA.class);
        when(savedEntity.getId()).thenReturn(10L);

        when(mapper.toEntity(cardapio)).thenReturn(entity);
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));
        when(repository.save(entity)).thenReturn(savedEntity);

        Long idResult = gateway.salvar(cardapio);

        assertEquals(10L, idResult);
        verify(repository).save(entity);
    }

    @Test
    void deveLancarExcecaoAoSalvarQuandoRestauranteNaoEncontrado() {
        Cardapio cardapio = mock(Cardapio.class);
        Restaurante restauranteDomain = mock(Restaurante.class);
        when(cardapio.getRestaurante()).thenReturn(restauranteDomain);
        when(restauranteDomain.getRestauranteId()).thenReturn(1L);

        CardapioEntityJPA entity = new CardapioEntityJPA();

        when(mapper.toEntity(cardapio)).thenReturn(entity);
        when(restauranteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> gateway.salvar(cardapio));
        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        Long id = 1L;
        CardapioEntityJPA entity = new CardapioEntityJPA();
        Cardapio cardapioDomain = mock(Cardapio.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(cardapioDomain);

        Optional<Cardapio> result = gateway.buscarPorId(id);

        assertTrue(result.isPresent());
        assertEquals(cardapioDomain, result.get());
    }

    @Test
    void deveRetornarVazioAoBuscarPorIdInexistente() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Cardapio> result = gateway.buscarPorId(id);

        assertTrue(result.isEmpty());
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveBuscarTodosPorRestauranteId() {
        Long restauranteId = 1L;
        CardapioEntityJPA entity1 = new CardapioEntityJPA();
        CardapioEntityJPA entity2 = new CardapioEntityJPA();
        Cardapio cardapioDomain1 = mock(Cardapio.class);
        Cardapio cardapioDomain2 = mock(Cardapio.class);

        when(repository.findAllByRestaurante_RestauranteId(restauranteId)).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(cardapioDomain1);
        when(mapper.toDomain(entity2)).thenReturn(cardapioDomain2);

        List<Cardapio> result = gateway.buscarTodosPorRestauranteId(restauranteId);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(cardapioDomain1, cardapioDomain2)));
    }

    @Test
    void deveBuscarTodos() {
        CardapioEntityJPA entity1 = new CardapioEntityJPA();
        CardapioEntityJPA entity2 = new CardapioEntityJPA();
        Cardapio cardapioDomain1 = mock(Cardapio.class);
        Cardapio cardapioDomain2 = mock(Cardapio.class);

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(cardapioDomain1);
        when(mapper.toDomain(entity2)).thenReturn(cardapioDomain2);

        List<Cardapio> result = gateway.buscarTodos();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(cardapioDomain1, cardapioDomain2)));
    }

    @Test
    void deveDeletarPorId() {
        Long id = 1L;

        gateway.deletarPorId(id);

        verify(repository).deleteById(id);
    }
}