package br.com.fiap.techchallengefase2.infra.gateway.jpa;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.mapper.UsuarioMapperJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
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
    private UsuarioGatewayJPA gateway;

    @Test
    void deveBuscarPorIdComSucesso() {
        Long id = 1L;
        UsuarioEntityJPA entity = new UsuarioEntityJPA();
        UsuarioBase domain = mock(UsuarioBase.class);

        when(repository.findByIdWithRelacionamentos(id)).thenReturn(Optional.of(entity));
        when(usuarioMapperJPA.toDomain(entity)).thenReturn(domain);

        Optional<UsuarioBase> result = gateway.buscarPorId(id);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void deveRetornarVazioAoBuscarPorIdInexistente() {
        Long id = 1L;
        when(repository.findByIdWithRelacionamentos(id)).thenReturn(Optional.empty());

        Optional<UsuarioBase> result = gateway.buscarPorId(id);

        assertTrue(result.isEmpty());
        verify(usuarioMapperJPA, never()).toDomain(any());
    }

    @Test
    void deveDeletarPorId() {
        Long id = 1L;

        gateway.deletarPorId(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        UsuarioBase inputDomain = mock(UsuarioBase.class);
        UsuarioEntityJPA entityToSave = new UsuarioEntityJPA();
        UsuarioEntityJPA savedEntity = new UsuarioEntityJPA();
        UsuarioBase outputDomain = mock(UsuarioBase.class);

        when(usuarioMapperJPA.toEntity(inputDomain)).thenReturn(entityToSave);
        when(repository.save(entityToSave)).thenReturn(savedEntity);
        when(usuarioMapperJPA.toDomain(savedEntity)).thenReturn(outputDomain);

        UsuarioBase result = gateway.salvar(inputDomain);

        assertEquals(outputDomain, result);
        verify(repository).save(entityToSave);
    }

    @Test
    void deveRetornarVerdadeiroSeUsuarioExisteComLogin() {
        String login = "admin";
        when(repository.findByLogin(login)).thenReturn(Optional.of(new UsuarioEntityJPA()));

        boolean result = gateway.existeUsuarioComLogin(login);

        assertTrue(result);
    }

    @Test
    void deveRetornarFalsoSeUsuarioNaoExisteComLogin() {
        String login = "admin";
        when(repository.findByLogin(login)).thenReturn(Optional.empty());

        boolean result = gateway.existeUsuarioComLogin(login);

        assertFalse(result);
    }

    @Test
    void deveRetornarVerdadeiroSeUsuarioExisteComEmail() {
        String email = "teste@teste.com";
        when(repository.findByEmail(email)).thenReturn(Optional.of(new UsuarioEntityJPA()));

        boolean result = gateway.existeUsuarioComEmail(email);

        assertTrue(result);
    }

    @Test
    void deveRetornarFalsoSeUsuarioNaoExisteComEmail() {
        String email = "teste@teste.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = gateway.existeUsuarioComEmail(email);

        assertFalse(result);
    }

    @Test
    void deveBuscarTodos() {
        UsuarioEntityJPA entity1 = new UsuarioEntityJPA();
        UsuarioEntityJPA entity2 = new UsuarioEntityJPA();
        UsuarioBase domain1 = mock(UsuarioBase.class);
        UsuarioBase domain2 = mock(UsuarioBase.class);

        when(repository.findAllWithRelacionamentos()).thenReturn(List.of(entity1, entity2));
        when(usuarioMapperJPA.toDomain(entity1)).thenReturn(domain1);
        when(usuarioMapperJPA.toDomain(entity2)).thenReturn(domain2);

        Collection<UsuarioBase> result = gateway.buscarTodos();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(domain1, domain2)));
    }

    @Test
    void deveBuscarPorLoginComSucesso() {
        String login = "admin";
        UsuarioEntityJPA entity = new UsuarioEntityJPA();
        UsuarioBase domain = mock(UsuarioBase.class);

        when(repository.findByLogin(login)).thenReturn(Optional.of(entity));
        when(usuarioMapperJPA.toDomain(entity)).thenReturn(domain);

        Optional<UsuarioBase> result = gateway.buscarPorLogin(login);

        assertTrue(result.isPresent());
        assertEquals(domain, result.get());
    }

    @Test
    void deveRetornarVazioAoBuscarPorLoginInexistente() {
        String login = "admin";
        when(repository.findByLogin(login)).thenReturn(Optional.empty());

        Optional<UsuarioBase> result = gateway.buscarPorLogin(login);

        assertTrue(result.isEmpty());
        verify(usuarioMapperJPA, never()).toDomain(any());
    }
}