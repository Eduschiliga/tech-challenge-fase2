package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.exception.usuario.CategoriaInvalidaException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioFactoryTest {

    @Test
    void obterInstancia_DeveRetornarTipoCastado_QuandoTipoForInstancia() {
        Dono dono = new Dono(1L, "Luana", "luana@email.com", "luana", "123", "End", new ArrayList<>(), new ArrayList<>());

        Dono result = UsuarioFactory.obterInstancia(dono, Dono.class);

        assertNotNull(result);
        assertEquals(dono, result);
    }

    @Test
    void obterInstancia_DeveLancarExcecao_QuandoTipoNaoForInstancia() {
        Cliente cliente = new Cliente(1L, "Cliente", "c@email.com", "cliente", "123", "End", new ArrayList<>());

        assertThrows(CategoriaInvalidaException.class, () -> UsuarioFactory.obterInstancia(cliente, Dono.class));
    }

    @Test
    void obterInstanciaDeAcordoComACategoria_DeveRetornarDono_QuandoCategoriaForZero() {
        Dono dono = new Dono(1L, "Dono", "d@email.com", "dono", "123", "End", new ArrayList<>(), new ArrayList<>());
        // Assuming Dono.getCategoria() returns 0

        UsuarioBase result = UsuarioFactory.obterInstanciaDeAcordoComACategoria(dono);

        assertTrue(result instanceof Dono);
    }

    @Test
    void obterInstanciaDeAcordoComACategoria_DeveRetornarCliente_QuandoCategoriaForUm() {
        Cliente cliente = new Cliente(1L, "Cliente", "c@email.com", "cliente", "123", "End", new ArrayList<>());
        // Assuming Cliente.getCategoria() returns 1

        UsuarioBase result = UsuarioFactory.obterInstanciaDeAcordoComACategoria(cliente);

        assertTrue(result instanceof Cliente);
    }

    @Test
    void criarUsuario_DeveInstanciarDono_QuandoCategoriaDonoIdForFornecido() {
        UsuarioBase result = UsuarioFactory.criarUsuario(0, "Luana", "l@email.com", "luana", "senha", "Rua X");

        assertTrue(result instanceof Dono);
        assertEquals("Luana", result.getNome());
        assertEquals("l@email.com", result.getEmail());
    }

    @Test
    void criarUsuario_DeveInstanciarCliente_QuandoCategoriaClienteIdForFornecido() {
        UsuarioBase result = UsuarioFactory.criarUsuario(1, "Cliente Teste", "c@email.com", "cliente", "senha", "Rua Y");

        assertTrue(result instanceof Cliente);
        assertEquals("Cliente Teste", result.getNome());
    }
}