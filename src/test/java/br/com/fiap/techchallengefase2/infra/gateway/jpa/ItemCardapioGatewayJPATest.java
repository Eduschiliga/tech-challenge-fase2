package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.CardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.ItemCardapioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.ItemCardapioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.CardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.ItemCardapioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.cardapio.CardapioNaoEncontradoException;
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
class ItemCardapioGatewayJPATest {

    @Mock
    private ItemCardapioRepository repository;

    @Mock
    private CardapioRepository cardapioRepository;

    @Mock
    private ItemCardapioMapperJPA mapper;

    @InjectMocks
    private ItemCardapioGatewayJPA itemCardapioGateway;

    private ItemCardapio itemCardapio;
    private ItemCardapioEntityJPA itemCardapioEntity;
    private CardapioEntityJPA cardapioEntity;
    private final Long cardapioId = 1L;
    private final Long itemCardapioId = 10L;

    @BeforeEach
    void setUp() {
        itemCardapio = new ItemCardapio(
                itemCardapioId,
                "Item Teste",
                "Descrição Teste",
                50.0,
                false,
                "/foto.jpg",
                cardapioId
        );

        cardapioEntity = new CardapioEntityJPA();
        cardapioEntity.setId(cardapioId);

        itemCardapioEntity = new ItemCardapioEntityJPA();
        itemCardapioEntity.setItemCardapioId(itemCardapioId);
        itemCardapioEntity.setCardapio(cardapioEntity);
    }

    @Test
    void deveSalvarItemCardapio_quandoCardapioExiste() {
        // Given
        when(mapper.toEntity(itemCardapio)).thenReturn(itemCardapioEntity);
        when(cardapioRepository.findById(cardapioId)).thenReturn(Optional.of(cardapioEntity));
        when(repository.save(itemCardapioEntity)).thenReturn(itemCardapioEntity);

        // When
        Long savedId = itemCardapioGateway.salvar(itemCardapio);

        // Then
        assertNotNull(savedId);
        assertEquals(itemCardapioId, savedId);
        verify(mapper, times(1)).toEntity(itemCardapio);
        verify(cardapioRepository, times(1)).findById(cardapioId);
        verify(repository, times(1)).save(itemCardapioEntity);
    }

    @Test
    void deveLancarExcecao_aoSalvarItemCardapio_quandoCardapioNaoExiste() {
        // Given
        when(mapper.toEntity(itemCardapio)).thenReturn(itemCardapioEntity);
        when(cardapioRepository.findById(cardapioId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CardapioNaoEncontradoException.class, () -> {
            itemCardapioGateway.salvar(itemCardapio);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPorId_quandoEncontrado() {
        // Given
        when(repository.findById(itemCardapioId)).thenReturn(Optional.of(itemCardapioEntity));
        when(mapper.toDomain(itemCardapioEntity)).thenReturn(itemCardapio);

        // When
        Optional<ItemCardapio> result = itemCardapioGateway.buscarPorId(itemCardapioId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(itemCardapio, result.get());
        verify(repository, times(1)).findById(itemCardapioId);
        verify(mapper, times(1)).toDomain(itemCardapioEntity);
    }

    @Test
    void deveBuscarPorId_quandoNaoEncontrado() {
        // Given
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<ItemCardapio> result = itemCardapioGateway.buscarPorId(id);

        // Then
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveBuscarTodosPorCardapioId() {
        // Given
        List<ItemCardapioEntityJPA> entityList = Collections.singletonList(itemCardapioEntity);
        when(repository.findAllByCardapio_Id(cardapioId)).thenReturn(entityList);
        when(mapper.toDomain(itemCardapioEntity)).thenReturn(itemCardapio);

        // When
        List<ItemCardapio> result = itemCardapioGateway.buscarTodosPorCardapioId(cardapioId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(itemCardapio, result.get(0));
        verify(repository, times(1)).findAllByCardapio_Id(cardapioId);
        verify(mapper, times(1)).toDomain(itemCardapioEntity);
    }

    @Test
    void deveDeletarPorId() {
        // Given
        doNothing().when(repository).deleteById(itemCardapioId);

        // When
        itemCardapioGateway.deletarPorId(itemCardapioId);

        // Then
        verify(repository, times(1)).deleteById(itemCardapioId);
    }
}