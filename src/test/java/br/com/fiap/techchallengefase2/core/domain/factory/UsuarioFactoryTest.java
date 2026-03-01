package br.com.fiap.techchallengefase2.core.domain.factory;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import br.com.fiap.techchallengefase2.core.domain.usuario.CategoriaUsuario;
import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioFactoryTest {

    @Test
    @DisplayName("Deve obter a instância correta quando o tipo for compatível")
    void deveObterInstanciaCorreta() {
        UsuarioBase usuarioDono = new Dono(1L, "Nome", "email", "login", "senha", "Endereço", new ArrayList<>());

        Dono donoRetornado = UsuarioFactory.obterInstancia(usuarioDono, Dono.class);

        assertNotNull(donoRetornado);
        assertEquals(1L, donoRetornado.getUsuarioId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar obter uma instância de tipo incompatível")
    void deveLancarExcecaoAoObterInstanciaIncompativel() {
        UsuarioBase usuarioCliente = new Cliente(1L, "Nome", "email", "login", "senha", "Endereço");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsuarioFactory.obterInstancia(usuarioCliente, Dono.class)
        );

        assertEquals("O usuário não é do tipo esperado: Dono", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar os dados parciais de um Dono mantendo a lista de restaurantes")
    void deveAtualizarDadosParciaisDeDono() {
        List<Restaurante> restaurantes = List.of(new Restaurante(10L, "Restaurante A"));
        Dono donoAtual = new Dono(1L, "Nome Antigo", "antigo@email.com", "login.antigo", "senha123", "Endereço Antigo", restaurantes);
        DadosUsuarioInputDTO dto = new DadosUsuarioInputDTO("Nome Novo", "novo@email.com", "login.novo", "Endereço Novo");

        UsuarioBase usuarioAtualizado = UsuarioFactory.atualizarDadosParciais(donoAtual, dto);

        assertInstanceOf(Dono.class, usuarioAtualizado);
        Dono donoAtualizado = (Dono) usuarioAtualizado;

        assertEquals(1L, donoAtualizado.getUsuarioId());
        assertEquals("Nome Novo", donoAtualizado.getNome());
        assertEquals("novo@email.com", donoAtualizado.getEmail());
        assertEquals("login.novo", donoAtualizado.getLogin());
        assertEquals("Endereço Novo", donoAtualizado.getEndereco());
        assertEquals("senha123", donoAtualizado.getSenha());
        assertEquals(1, donoAtualizado.getRestaurantes().size());
    }

    @Test
    @DisplayName("Deve atualizar os dados parciais de um Cliente")
    void deveAtualizarDadosParciaisDeCliente() {
        Cliente clienteAtual = new Cliente(2L, "Nome Antigo", "antigo@email.com", "login.antigo", "senha123", "Endereço Antigo");
        DadosUsuarioInputDTO dto = new DadosUsuarioInputDTO("Nome Novo", "novo@email.com", "login.novo", "Endereço Novo");

        UsuarioBase usuarioAtualizado = UsuarioFactory.atualizarDadosParciais(clienteAtual, dto);

        assertInstanceOf(Cliente.class, usuarioAtualizado);
        assertEquals(2L, usuarioAtualizado.getUsuarioId());
        assertEquals("Nome Novo", usuarioAtualizado.getNome());
        assertEquals("novo@email.com", usuarioAtualizado.getEmail());
        assertEquals("Endereço Novo", usuarioAtualizado.getEndereco());
        assertEquals("senha123", usuarioAtualizado.getSenha());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um usuário nulo")
    void deveLancarExcecaoAoAtualizarUsuarioNulo() {
        DadosUsuarioInputDTO dto = new DadosUsuarioInputDTO("Nome", "email", "login", "Endereço");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsuarioFactory.atualizarDadosParciais(null, dto)
        );

        assertEquals("Usuário base não pode ser null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um tipo de usuário não suportado")
    void deveLancarExcecaoAoAtualizarTipoNaoSuportado() {
        DadosUsuarioInputDTO dto = new DadosUsuarioInputDTO("Nome", "email", "login", "Endereço");

        // Criando uma classe anônima para simular um tipo não mapeado no switch
        UsuarioBase usuarioInvalido = new UsuarioBase(1L, "Nome", "email", "login", "senha", "Endereço", 99) {
        };

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsuarioFactory.atualizarDadosParciais(usuarioInvalido, dto)
        );

        assertEquals("Tipo de usuário não suportado na atualização", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar um usuário do tipo Dono")
    void deveCriarUsuarioDono() {
        UsuarioBase usuarioBase = UsuarioFactory.criarUsuario(
                CategoriaUsuario.DONO.getCodigo(),
                "Dono Teste",
                "dono@teste.com",
                "dono.login",
                "senha123",
                "Endereço"
        );

        assertInstanceOf(Dono.class, usuarioBase);
        assertEquals("Dono Teste", usuarioBase.getNome());
        assertNull(usuarioBase.getUsuarioId());
        assertNotNull(((Dono) usuarioBase).getRestaurantes());
        assertTrue(((Dono) usuarioBase).getRestaurantes().isEmpty());
    }

    @Test
    @DisplayName("Deve criar um usuário do tipo Cliente")
    void deveCriarUsuarioCliente() {
        UsuarioBase usuarioBase = UsuarioFactory.criarUsuario(
                CategoriaUsuario.CLIENTE.getCodigo(),
                "Cliente Teste",
                "cliente@teste.com",
                "cliente.login",
                "senha123",
                "Endereço"
        );

        assertInstanceOf(Cliente.class, usuarioBase);
        assertEquals("Cliente Teste", usuarioBase.getNome());
        assertNull(usuarioBase.getUsuarioId());
    }

    @Test
    @DisplayName("Deve propagar exceção caso a categoria enviada para criação seja inválida")
    void deveLancarExcecaoAoCriarUsuarioComCategoriaInvalida() {
        Integer categoriaInvalida = 99;

        assertThrows(IllegalArgumentException.class, () ->
                UsuarioFactory.criarUsuario(
                        categoriaInvalida,
                        "Nome",
                        "email@teste.com",
                        "login",
                        "senha123",
                        "Endereço"
                )
        );
    }
}