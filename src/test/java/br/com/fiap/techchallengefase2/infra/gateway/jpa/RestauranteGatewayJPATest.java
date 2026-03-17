package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.RestauranteMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.UsuarioNaoEncontradoException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteGatewayJPATest {

    @Mock
    private RestauranteRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RestauranteMapperJPA mapper;

    @InjectMocks
    private RestauranteGatewayJPA restauranteGateway;

    private Restaurante restaurante;
    private RestauranteEntityJPA restauranteEntity;
    private UsuarioEntityJPA usuarioEntity;
    private final Long restauranteId = 1L;
    private final Long usuarioId = 10L;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante(
                restauranteId,
                "Restaurante Teste",
                null,
                null,
                null,
                usuarioId
        );

        usuarioEntity = new UsuarioEntityJPA();
        usuarioEntity.setUsuarioId(usuarioId);

        restauranteEntity = new RestauranteEntityJPA();
        restauranteEntity.setRestauranteId(restauranteId);
        restauranteEntity.setUsuario(usuarioEntity);
    }

    @Test
    void deveSalvarRestaurante_quandoUsuarioExiste() {
        // Given
        when(mapper.toEntity(restaurante)).thenReturn(restauranteEntity);
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));
        when(repository.save(restauranteEntity)).thenReturn(restauranteEntity);

        // When
        Long savedId = restauranteGateway.salvar(restaurante);

        // Then
        assertNotNull(savedId);
        assertEquals(restauranteId, savedId);
        verify(mapper, times(1)).toEntity(restaurante);
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(repository, times(1)).save(restauranteEntity);
    }

    @Test
    void deveLancarExcecao_aoSalvarRestaurante_quandoUsuarioNaoExiste() {
        // Given
        when(mapper.toEntity(restaurante)).thenReturn(restauranteEntity);
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            restauranteGateway.salvar(restaurante);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPorId_quandoEncontrado() {
        // Given
        when(repository.findById(restauranteId)).thenReturn(Optional.of(restauranteEntity));
        when(mapper.toDomain(restauranteEntity)).thenReturn(restaurante);

        // When
        Optional<Restaurante> result = restauranteGateway.buscarPorId(restauranteId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(restaurante, result.get());
        verify(repository, times(1)).findById(restauranteId);
        verify(mapper, times(1)).toDomain(restauranteEntity);
    }

    @Test
    void deveBuscarPorId_quandoNaoEncontrado() {
        // Given
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Restaurante> result = restauranteGateway.buscarPorId(id);

        // Then
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveBuscarTodosPorUsuarioId() {
        // Given
        List<RestauranteEntityJPA> entityList = Collections.singletonList(restauranteEntity);
        when(repository.findAllByUsuario_UsuarioId(usuarioId)).thenReturn(entityList);
        when(mapper.toDomain(restauranteEntity)).thenReturn(restaurante);

        // When
        List<Restaurante> result = restauranteGateway.buscarTodosPorUsuarioId(usuarioId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(restaurante, result.get(0));
        verify(repository, times(1)).findAllByUsuario_UsuarioId(usuarioId);
        verify(mapper, times(1)).toDomain(restauranteEntity);
    }

    @Test
    void deveBuscarTodos() {
        // Given
        List<RestauranteEntityJPA> entityList = Collections.singletonList(restauranteEntity);
        when(repository.findAll()).thenReturn(entityList);
        when(mapper.toDomain(restauranteEntity)).thenReturn(restaurante);

        // When
        List<Restaurante> result = restauranteGateway.buscarTodos();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(restaurante, result.get(0));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDomain(restauranteEntity);
    }

    @Test
    void deveDeletarPorId() {
        // Given
        doNothing().when(repository).deleteById(restauranteId);

        // When
        restauranteGateway.deletarPorId(restauranteId);

        // Then
        verify(repository, times(1)).deleteById(restauranteId);
    }
}