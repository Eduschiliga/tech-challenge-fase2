package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.TipoUsuarioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.TipoUsuarioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.exception.RestauranteNaoEncontradoException;
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
class TipoUsuarioGatewayJPATest {

    @Mock
    private TipoUsuarioRepository repository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private TipoUsuarioMapperJPA mapper;

    @InjectMocks
    private TipoUsuarioGatewayJPA tipoUsuarioGateway;

    private TipoUsuario tipoUsuario;
    private TipoUsuarioEntityJPA tipoUsuarioEntity;
    private RestauranteEntityJPA restauranteEntity;
    private final Long tipoUsuarioId = 1L;
    private final Long restauranteId = 10L;

    @BeforeEach
    void setUp() {
        tipoUsuario = new TipoUsuario(tipoUsuarioId, restauranteId, "Admin");

        restauranteEntity = new RestauranteEntityJPA();
        restauranteEntity.setRestauranteId(restauranteId);

        tipoUsuarioEntity = new TipoUsuarioEntityJPA();
        tipoUsuarioEntity.setTipoUsuarioId(tipoUsuarioId);
        tipoUsuarioEntity.setRestaurante(restauranteEntity);
    }

    @Test
    void deveSalvarTipoUsuario_quandoRestauranteExiste() {
        // Given
        when(mapper.toEntity(tipoUsuario)).thenReturn(tipoUsuarioEntity);
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restauranteEntity));
        when(repository.save(tipoUsuarioEntity)).thenReturn(tipoUsuarioEntity);

        // When
        Long savedId = tipoUsuarioGateway.salvar(tipoUsuario);

        // Then
        assertNotNull(savedId);
        assertEquals(tipoUsuarioId, savedId);
        verify(mapper, times(1)).toEntity(tipoUsuario);
        verify(restauranteRepository, times(1)).findById(restauranteId);
        verify(repository, times(1)).save(tipoUsuarioEntity);
    }

    @Test
    void deveLancarExcecao_aoSalvarTipoUsuario_quandoRestauranteNaoExiste() {
        // Given
        when(mapper.toEntity(tipoUsuario)).thenReturn(tipoUsuarioEntity);
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RestauranteNaoEncontradoException.class, () -> {
            tipoUsuarioGateway.salvar(tipoUsuario);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void deveBuscarPorId_quandoEncontrado() {
        // Given
        when(repository.findById(tipoUsuarioId)).thenReturn(Optional.of(tipoUsuarioEntity));
        when(mapper.toDomain(tipoUsuarioEntity)).thenReturn(tipoUsuario);

        // When
        Optional<TipoUsuario> result = tipoUsuarioGateway.buscarPorId(tipoUsuarioId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(tipoUsuario, result.get());
        verify(repository, times(1)).findById(tipoUsuarioId);
        verify(mapper, times(1)).toDomain(tipoUsuarioEntity);
    }

    @Test
    void deveBuscarPorId_quandoNaoEncontrado() {
        // Given
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<TipoUsuario> result = tipoUsuarioGateway.buscarPorId(id);

        // Then
        assertFalse(result.isPresent());
        verify(repository, times(1)).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void deveDeletarPorId() {
        // Given
        doNothing().when(repository).deleteById(tipoUsuarioId);

        // When
        tipoUsuarioGateway.deletarPorId(tipoUsuarioId);

        // Then
        verify(repository, times(1)).deleteById(tipoUsuarioId);
    }

    @Test
    void deveBuscarTodosPorRestauranteId() {
        // Given
        List<TipoUsuarioEntityJPA> entityList = Collections.singletonList(tipoUsuarioEntity);
        when(repository.findAllByRestaurante_RestauranteId(restauranteId)).thenReturn(entityList);
        when(mapper.toDomain(tipoUsuarioEntity)).thenReturn(tipoUsuario);

        // When
        List<TipoUsuario> result = tipoUsuarioGateway.buscarTodosPorRestauranteId(restauranteId);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(tipoUsuario, result.get(0));
        verify(repository, times(1)).findAllByRestaurante_RestauranteId(restauranteId);
        verify(mapper, times(1)).toDomain(tipoUsuarioEntity);
    }
}