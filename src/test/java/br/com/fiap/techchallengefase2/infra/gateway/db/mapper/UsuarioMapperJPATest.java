package br.com.fiap.techchallengefase2.infra.gateway.db.mapper;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioMapperJPATest {

    @Mock
    private TipoUsuarioMapperJPA tipoUsuarioMapperJPA;

    @Mock
    private RestauranteMapperJPA restauranteMapperJPA;

    @InjectMocks
    private UsuarioMapperJPA usuarioMapperJPA;

    @Test
    void toDomain_DeveMapearParaDono() {
        UsuarioEntityJPA entity = new UsuarioEntityJPA(
                1L, "Nome", "Endereco", "Email", "Login", "Senha",
                CategoriaUsuario.DONO, Set.of(new RestauranteEntityJPA()), Set.of(new TipoUsuarioEntityJPA())
        );

        when(tipoUsuarioMapperJPA.toDomain(any())).thenReturn(mock(TipoUsuario.class));
        when(restauranteMapperJPA.toDomain(any())).thenReturn(mock(Restaurante.class));

        UsuarioBase result = usuarioMapperJPA.toDomain(entity);

        assertTrue(result instanceof Dono);
        assertEquals(1L, result.getUsuarioId());
        assertEquals("Nome", result.getNome());
        assertFalse(((Dono) result).getRestaurantes().isEmpty());
        assertFalse(result.getTipoUsuarioList().isEmpty());
    }

    @Test
    void toDomain_DeveMapearParaCliente() {
        UsuarioEntityJPA entity = new UsuarioEntityJPA(
                2L, "Cliente", "Endereco", "Email", "Login", "Senha",
                CategoriaUsuario.CLIENTE, null, Set.of(new TipoUsuarioEntityJPA())
        );

        when(tipoUsuarioMapperJPA.toDomain(any())).thenReturn(mock(TipoUsuario.class));

        UsuarioBase result = usuarioMapperJPA.toDomain(entity);

        assertTrue(result instanceof Cliente);
        assertEquals(2L, result.getUsuarioId());
        assertEquals("Cliente", result.getNome());
        assertFalse(result.getTipoUsuarioList().isEmpty());
    }

    @Test
    void toEntity_DeveMapearDono() {
        Dono dono = new Dono(
                1L, "Dono", "email@email.com", "login", "senha", "Endereco",
                List.of(mock(Restaurante.class)), List.of(mock(TipoUsuario.class))
        );

        when(tipoUsuarioMapperJPA.toEntity(any(TipoUsuario.class))).thenReturn(new TipoUsuarioEntityJPA());
        when(restauranteMapperJPA.toEntity(any(Restaurante.class))).thenReturn(new RestauranteEntityJPA());

        UsuarioEntityJPA result = usuarioMapperJPA.toEntity(dono);

        assertNotNull(result);
        assertEquals(CategoriaUsuario.DONO, result.getCategoria());
        assertEquals(1L, result.getUsuarioId());
        assertFalse(result.getRestauranteList().isEmpty());
        assertFalse(result.getTipoUsuarioList().isEmpty());
    }

    @Test
    void toEntity_DeveMapearCliente() {
        Cliente cliente = new Cliente(
                2L, "Cliente", "email@email.com", "login", "senha", "Endereco",
                List.of(mock(TipoUsuario.class))
        );

        when(tipoUsuarioMapperJPA.toEntity(any(TipoUsuario.class))).thenReturn(new TipoUsuarioEntityJPA());

        UsuarioEntityJPA result = usuarioMapperJPA.toEntity(cliente);

        assertNotNull(result);
        assertEquals(CategoriaUsuario.CLIENTE, result.getCategoria());
        assertEquals(2L, result.getUsuarioId());
        assertTrue(result.getRestauranteList().isEmpty());
        assertFalse(result.getTipoUsuarioList().isEmpty());
    }

    @Test
    void toDomain_DeveTratarListasNulas() {
        UsuarioEntityJPA entity = new UsuarioEntityJPA(
                1L, "Nome", "Endereco", "Email", "Login", "Senha",
                CategoriaUsuario.DONO, null, null
        );

        UsuarioBase result = usuarioMapperJPA.toDomain(entity);

        assertTrue(result instanceof Dono);
        assertTrue(result.getTipoUsuarioList().isEmpty());
        assertTrue(((Dono) result).getRestaurantes().isEmpty());
    }

    @Test
    void toEntity_DeveTratarListasNulas() {
        Dono dono = new Dono(
                1L, "Dono", "email@email.com", "login", "senha", "Endereco",
                null, null
        );

        UsuarioEntityJPA result = usuarioMapperJPA.toEntity(dono);

        assertNotNull(result);
        assertTrue(result.getTipoUsuarioList().isEmpty());
        assertTrue(result.getRestauranteList().isEmpty());
    }
}