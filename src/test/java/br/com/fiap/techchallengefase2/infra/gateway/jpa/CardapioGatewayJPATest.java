package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Cardapio;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.CardapioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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

    @InjectMocks
    private CardapioGatewayJPA cardapioGateway;

    private Cardapio cardapio;
    private CardapioEntityJPA cardapioEntity;

    @BeforeEach
    void setUp() {
        cardapio = new Cardapio(1L, null, null, "Cardápio Teste");
        cardapioEntity = new CardapioEntityJPA();
        cardapioEntity.setId(1L);
    }

    @Test
    void deveSalvarCardapio() {
        // Given
        when(mapper.toEntity(cardapio)).thenReturn(cardapioEntity);
        when(repository.save(cardapioEntity)).thenReturn(cardapioEntity);

        // When
        Long savedId = cardapioGateway.salvar(cardapio);

        // Then
        assertNotNull(savedId);
        assertEquals(cardapioEntity.getId(), savedId);
        verify(mapper, times(1)).toEntity(cardapio);
        verify(repository, times(1)).save(cardapioEntity);
    }

    @Test
    void deveBuscarPorId_quandoEncontrado() {
        // Given
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(cardapioEntity));
        when(mapper.toDomain(cardapioEntity)).thenReturn(cardapio);

        // When
        Optional<Cardapio> result = cardapioGateway.buscarPorId(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(cardapio, result.get());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDomain(cardapioEntity);
    }

    @Test
    void deveBuscarPorId_quandoNaoEncontrado() {
        // Given
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Cardapio> result = cardapioGateway.buscarPorId(id);

        // Then
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveBuscarTodosPorRestauranteId() {
        // Given
        Long restauranteId = 1L;
        List<CardapioEntityJPA> entityList = Collections.singletonList(cardapioEntity);
        when(repository.findAllByRestaurante_RestauranteId(restauranteId)).thenReturn(entityList);
        when(mapper.toDomain(cardapioEntity)).thenReturn(cardapio);

        // When
        List<Cardapio> result = cardapioGateway.buscarTodosPorRestauranteId(restauranteId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(cardapio, result.get(0));
        verify(repository, times(1)).findAllByRestaurante_RestauranteId(restauranteId);
        verify(mapper, times(1)).toDomain(cardapioEntity);
    }

    @Test
    void deveBuscarTodos() {
        // Given
        List<CardapioEntityJPA> entityList = Collections.singletonList(cardapioEntity);
        when(repository.findAll()).thenReturn(entityList);
        when(mapper.toDomain(cardapioEntity)).thenReturn(cardapio);

        // When
        List<Cardapio> result = cardapioGateway.buscarTodos();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(cardapio, result.get(0));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDomain(cardapioEntity);
    }

    @Test
    void deveDeletarPorId() {
        // Given
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        // When
        cardapioGateway.deletarPorId(id);

        // Then
        verify(repository, times(1)).deleteById(id);
    }
}