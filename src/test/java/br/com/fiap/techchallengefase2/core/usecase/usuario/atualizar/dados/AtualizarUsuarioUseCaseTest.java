package br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.dados;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteEmail;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteLogin;
import br.com.fiap.techchallengefase2.core.usecase.ususario.atualizar.dados.AtualizarUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarUsuarioUseCaseTest {

    @InjectMocks
    private AtualizarUsuarioUseCase atualizarUsuarioUseCase;

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private RuleDadosUsuario ruleDadosUsuario;

    @Mock
    private ValidaSeJaExisteEmail ruleExisteEmail;

    @Mock
    private ValidaSeJaExisteLogin ruleExisteLogin;

    @Test
    @DisplayName("Deve atualizar dados do usuário com sucesso quando não há alteração de e-mail ou login")
    void deveAtualizarUsuarioComSucessoSemAlterarCredenciais() {
        // Arrange
        Long usuarioId = 1L;
        String nomeOriginal = "João Silva";
        String email = "joao@email.com";
        String login = "joao.login";

        DadosUsuarioInputDTO input = new DadosUsuarioInputDTO("João Silva Alterado", email, login, "Novo Endereço");

        Cliente usuarioAtual = new Cliente(usuarioId, nomeOriginal, email, login, "senha123", "Endereço Antigo");

        atualizarUsuarioUseCase = new AtualizarUsuarioUseCase(
                buscarUsuarioPorIdUseCase,
                usuarioGateway,
                List.of(ruleDadosUsuario),
                List.of(ruleExisteEmail, ruleExisteLogin)
        );

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
        when(usuarioGateway.salvar(any(UsuarioBase.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioBase resultado = atualizarUsuarioUseCase.atualizar(usuarioId, input);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva Alterado", resultado.getNome());
        assertEquals("Novo Endereço", resultado.getEndereco());

        verify(ruleDadosUsuario).validar(any());
        // Como o email e login não mudaram, as regras de credenciais não devem ser chamadas
        verify(ruleExisteEmail, never()).validar(any());
        verify(ruleExisteLogin, never()).validar(any());
        verify(usuarioGateway).salvar(any());
    }

    @Test
    @DisplayName("Deve validar disponibilidade quando o e-mail for alterado")
    void deveValidarCredenciaisQuandoEmailForAlterado() {
        // Arrange
        Long usuarioId = 1L;
        DadosUsuarioInputDTO input = new DadosUsuarioInputDTO("Nome", "novo@email.com", "login", "End");
        Cliente usuarioAtual = new Cliente(usuarioId, "Nome", "antigo@email.com", "login", "senha", "End");

        atualizarUsuarioUseCase = new AtualizarUsuarioUseCase(
                buscarUsuarioPorIdUseCase,
                usuarioGateway,
                List.of(ruleDadosUsuario),
                List.of(ruleExisteEmail)
        );

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
        when(usuarioGateway.salvar(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        atualizarUsuarioUseCase.atualizar(usuarioId, input);

        // Assert
        verify(ruleExisteEmail).validar(argThat(u -> u.getEmail().equals("novo@email.com")));
        verify(usuarioGateway).salvar(any());
    }

    @Test
    @DisplayName("Deve validar disponibilidade quando o login for alterado")
    void deveValidarCredenciaisQuandoLoginForAlterado() {
        // Arrange
        Long usuarioId = 1L;
        DadosUsuarioInputDTO input = new DadosUsuarioInputDTO("Nome", "email@test.com", "novo.login", "End");
        Cliente usuarioAtual = new Cliente(usuarioId, "Nome", "email@test.com", "antigo.login", "senha", "End");

        atualizarUsuarioUseCase = new AtualizarUsuarioUseCase(
                buscarUsuarioPorIdUseCase,
                usuarioGateway,
                List.of(ruleDadosUsuario),
                List.of(ruleExisteLogin)
        );

        when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
        when(usuarioGateway.salvar(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        atualizarUsuarioUseCase.atualizar(usuarioId, input);

        // Assert
        verify(ruleExisteLogin).validar(argThat(u -> u.getLogin().equals("novo.login")));
        verify(usuarioGateway).salvar(any());
    }
}