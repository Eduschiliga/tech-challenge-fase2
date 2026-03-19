package br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.exception.usuario.CategoriaInvalidaException;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIdUseCaseTest {

    @InjectMocks
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Test
    @DisplayName("Deve retornar um Dono quando a categoria for 0")
    void deveRetornarDonoQuandoCategoriaForZero() {
        Long usuarioId = 1L;
        Dono dono = new Dono(
                usuarioId,
                "Dono Teste",
                "dono@test.com",
                "dono.login",
                "senha",
                "End",
                new ArrayList<>(),
                new ArrayList<>()
        );

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(dono));

        UsuarioBase resultado = buscarUsuarioPorIdUseCase.buscarPorId(usuarioId);

        assertTrue(resultado instanceof Dono);
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(0, resultado.getCategoria());
    }

    @Test
    @DisplayName("Deve retornar um Cliente quando a categoria for 1")
    void deveRetornarClienteQuandoCategoriaForUm() {
        Long usuarioId = 2L;
        Cliente cliente = new Cliente(
                usuarioId,
                "Cliente Teste",
                "cli@test.com",
                "cli.login",
                "senha",
                "End",
                new ArrayList<>()
        );

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(cliente));

        UsuarioBase resultado = buscarUsuarioPorIdUseCase.buscarPorId(usuarioId);

        assertTrue(resultado instanceof Cliente);
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(1, resultado.getCategoria());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        Long usuarioId = 1L;
        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () ->
                buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}