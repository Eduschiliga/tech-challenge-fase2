package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.UsuarioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioGatewayJPATest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapperJPA usuarioMapperJPA;

    @InjectMocks
    private UsuarioGatewayJPA usuarioGateway;

    private UsuarioBase usuarioBase;
    private UsuarioEntityJPA usuarioEntity;
    private final Long usuarioId = 1L;

    @BeforeEach
    void setUp() {
        // Como UsuarioBase é abstrata, podemos mocká-la para os testes
        usuarioBase = mock(UsuarioBase.class);
        usuarioEntity = new UsuarioEntityJPA();
        usuarioEntity.setUsuarioId(usuarioId);
        usuarioEntity.setLogin("testuser");
        usuarioEntity.setEmail("test@example.com");
    }

    @Test
    void deveBuscarPorId_quandoEncontrado() {
        // Given
        when(repository.findByIdWithRelacionamentos(usuarioId)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioMapperJPA.toDomain(usuarioEntity)).thenReturn(usuarioBase);

        // When
        Optional<UsuarioBase> result = usuarioGateway.buscarPorId(usuarioId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(usuarioBase, result.get());
        verify(repository, times(1)).findByIdWithRelacionamentos(usuarioId);
        verify(usuarioMapperJPA, times(1)).toDomain(usuarioEntity);
    }

    @Test
    void deveBuscarPorId_quandoNaoEncontrado() {
        // Given
        when(repository.findByIdWithRelacionamentos(usuarioId)).thenReturn(Optional.empty());

        // When
        Optional<UsuarioBase> result = usuarioGateway.buscarPorId(usuarioId);

        // Then
        assertFalse(result.isPresent());
        verify(repository, times(1)).findByIdWithRelacionamentos(usuarioId);
        verify(usuarioMapperJPA, never()).toDomain(any());
    }

    @Test
    void deveDeletarPorId() {
        // Given
        doNothing().when(repository).deleteById(usuarioId);

        // When
        usuarioGateway.deletarPorId(usuarioId);

        // Then
        verify(repository, times(1)).deleteById(usuarioId);
    }

    @Test
    void deveSalvarUsuario() {
        // Given
        when(usuarioMapperJPA.toEntity(usuarioBase)).thenReturn(usuarioEntity);
        when(repository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapperJPA.toDomain(usuarioEntity)).thenReturn(usuarioBase);

        // When
        UsuarioBase savedUsuario = usuarioGateway.salvar(usuarioBase);

        // Then
        assertNotNull(savedUsuario);
        assertEquals(usuarioBase, savedUsuario);
        verify(usuarioMapperJPA, times(1)).toEntity(usuarioBase);
        verify(repository, times(1)).save(usuarioEntity);
        verify(usuarioMapperJPA, times(1)).toDomain(usuarioEntity);
    }

    @Test
    void deveRetornarTrue_quandoExisteUsuarioComLogin() {
        // Given
        String login = "testuser";
        when(repository.findByLogin(login)).thenReturn(Optional.of(usuarioEntity));

        // When
        boolean result = usuarioGateway.existeUsuarioComLogin(login);

        // Then
        assertTrue(result);
        verify(repository, times(1)).findByLogin(login);
    }

    @Test
    void deveRetornarFalse_quandoNaoExisteUsuarioComLogin() {
        // Given
        String login = "nonexistentuser";
        when(repository.findByLogin(login)).thenReturn(Optional.empty());

        // When
        boolean result = usuarioGateway.existeUsuarioComLogin(login);

        // Then
        assertFalse(result);
        verify(repository, times(1)).findByLogin(login);
    }

    @Test
    void deveRetornarTrue_quandoExisteUsuarioComEmail() {
        // Given
        String email = "test@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.of(usuarioEntity));

        // When
        boolean result = usuarioGateway.existeUsuarioComEmail(email);

        // Then
        assertTrue(result);
        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    void deveRetornarFalse_quandoNaoExisteUsuarioComEmail() {
        // Given
        String email = "nonexistent@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        // When
        boolean result = usuarioGateway.existeUsuarioComEmail(email);

        // Then
        assertFalse(result);
        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    void deveBuscarTodos() {
        // Given
        when(repository.findAllWithRelacionamentos()).thenReturn(Collections.singletonList(usuarioEntity));
        when(usuarioMapperJPA.toDomain(usuarioEntity)).thenReturn(usuarioBase);

        // When
        Collection<UsuarioBase> result = usuarioGateway.buscarTodos();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.contains(usuarioBase));
        verify(repository, times(1)).findAllWithRelacionamentos();
        verify(usuarioMapperJPA, times(1)).toDomain(usuarioEntity);
    }
}