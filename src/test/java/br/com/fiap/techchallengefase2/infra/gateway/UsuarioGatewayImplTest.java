package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.RestauranteRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.TipoUsuarioRepository;
import br.com.fiap.techchallengefase2.infra.gateway.db.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioGatewayImplTest {

    @InjectMocks
    private UsuarioGatewayImpl usuarioGateway;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Test
    void deveBuscarDonoPorIdComSucessoEPreencherListas() {
        UsuarioEntityJPA entity = UsuarioEntityJPA.builder()
                .usuarioId(1L)
                .nome("Nome")
                .endereco("End")
                .email("email@teste.com")
                .login("login")
                .senha("senha")
                .categoria(CategoriaUsuario.DONO)
                .build();

        TipoUsuarioEntityJPA tipo = TipoUsuarioEntityJPA.builder().tipoUsuarioId(1L).nome("Admin").build();
        RestauranteEntityJPA rest = RestauranteEntityJPA.builder().restauranteId(10L).nome("Rest").build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(tipoUsuarioRepository.findAllByUsuarioId(1L)).thenReturn(List.of(tipo));
        when(restauranteRepository.findAllByUsuarioId(1L)).thenReturn(List.of(rest));

        Optional<UsuarioBase> resultado = usuarioGateway.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertInstanceOf(Dono.class, resultado.get());

        Dono dono = (Dono) resultado.get();
        assertEquals(1, dono.getTipoUsuarioList().size());
        assertEquals(1, dono.getRestaurantes().size());
        assertEquals("Nome", dono.getNome());
    }

    @Test
    void deveBuscarClientePorIdComSucessoEPreencherListas() {
        UsuarioEntityJPA entity = UsuarioEntityJPA.builder()
                .usuarioId(2L)
                .nome("Nome")
                .endereco("End")
                .email("email@teste.com")
                .login("login")
                .senha("senha")
                .categoria(CategoriaUsuario.CLIENTE)
                .build();

        TipoUsuarioEntityJPA tipo = TipoUsuarioEntityJPA.builder().tipoUsuarioId(1L).nome("Cliente VIP").build();

        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(entity));
        when(tipoUsuarioRepository.findAllByUsuarioId(2L)).thenReturn(List.of(tipo));

        Optional<UsuarioBase> resultado = usuarioGateway.buscarPorId(2L);

        assertTrue(resultado.isPresent());
        assertInstanceOf(Cliente.class, resultado.get());

        Cliente cliente = (Cliente) resultado.get();
        assertEquals(1, cliente.getTipoUsuarioList().size());
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Cliente cliente = new Cliente(1L, "Nome", "email@t.com", "login", "senha", "End", new ArrayList<>());
        UsuarioEntityJPA entitySalva = UsuarioEntityJPA.builder()
                .usuarioId(1L)
                .categoria(CategoriaUsuario.CLIENTE)
                .build();

        when(usuarioRepository.save(any(UsuarioEntityJPA.class))).thenReturn(entitySalva);

        UsuarioBase resultado = usuarioGateway.salvar(cliente);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getUsuarioId());
        verify(usuarioRepository).save(any(UsuarioEntityJPA.class));
    }

    @Test
    void deveAtualizarSenhaComSucesso() {
        UsuarioEntityJPA entity = UsuarioEntityJPA.builder()
                .usuarioId(1L)
                .senha("senhaVelha")
                .categoria(CategoriaUsuario.CLIENTE)
                .build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(usuarioRepository.save(any(UsuarioEntityJPA.class))).thenReturn(entity);

        UsuarioBase resultado = usuarioGateway.atualizarSenha("novaSenha", 1L);

        assertEquals("novaSenha", entity.getSenha());
        assertNotNull(resultado);
        verify(usuarioRepository).save(entity);
    }

    @Test
    void deveRetornarTrueSeExisteUsuarioComLogin() {
        when(usuarioRepository.findByLogin("login123")).thenReturn(Optional.of(new UsuarioEntityJPA()));
        assertTrue(usuarioGateway.existeUsuarioComLogin("login123"));
    }

    @Test
    void deveRetornarTrueSeExisteUsuarioComEmail() {
        when(usuarioRepository.findByEmail("email@teste.com")).thenReturn(Optional.of(new UsuarioEntityJPA()));
        assertTrue(usuarioGateway.existeUsuarioComEmail("email@teste.com"));
    }

    @Test
    void deveBuscarTodosComSucesso() {
        UsuarioEntityJPA entity1 = UsuarioEntityJPA.builder().usuarioId(1L).categoria(CategoriaUsuario.CLIENTE).build();
        UsuarioEntityJPA entity2 = UsuarioEntityJPA.builder().usuarioId(2L).categoria(CategoriaUsuario.DONO).build();

        when(usuarioRepository.findAll()).thenReturn(List.of(entity1, entity2));

        Collection<UsuarioBase> resultados = usuarioGateway.buscarTodos();

        assertEquals(2, resultados.size());
    }

    @Test
    void deveDeletarPorIdComSucesso() {
        usuarioGateway.deletarPorId(1L);
        verify(usuarioRepository).deleteById(1L);
    }
}