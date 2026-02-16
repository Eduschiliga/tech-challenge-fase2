package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.ValidaSePossuiSenha;
import br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.dados.AtualizarDadosParciaisDadosParciaisUsuarioUseCase;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarDadosParciaisDadosParciaisUsuarioUseCaseTest {

    @Mock
    private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private RuleDadosUsuario ruleDadosUsuario;

    @Mock
    private RuleCredenciaisUsuario ruleCredenciaisUsuario;

    private AtualizarDadosParciaisDadosParciaisUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
                buscarUsuarioPorIdUseCase,
                usuarioGateway,
                List.of(ruleDadosUsuario),
                List.of(ruleCredenciaisUsuario)
        );
    }

    private UsuarioBase criarUsuario(Long id, String nome, String email, String login, String endereco) {
        return new Cliente(id, nome, email, login, "senha123", endereco);
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class TestesAtualizacaoSucesso {

        @Test
        @DisplayName("Deve atualizar dados parciais do usuário com sucesso")
        void deveAtualizarDadosParciaisComSucesso() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome Antigo", "antigo@email.com", "loginAntigo", "Endereco Antigo");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@email.com", "loginNovo", "Endereco Novo");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCase.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            assertEquals(usuarioId, resultado);
            verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioId);
            verify(ruleDadosUsuario).validar(usuarioAtualizar);
            verify(ruleCredenciaisUsuario).validar(usuarioAtual, usuarioAtualizar);
            verify(usuarioGateway).salvar(usuarioAtual);
        }

        @Test
        @DisplayName("Deve atualizar dados e manter dados existentes quando novos são nulos")
        void deveAtualizarDadosPermitindoNulos() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome Antigo", "antigo@email.com", "loginAntigo", "Endereco Antigo");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, null, null, null, null);

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCase.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            assertEquals(usuarioId, resultado);
            verify(usuarioGateway).salvar(usuarioAtual);
        }
    }

    @Nested
    @DisplayName("Testes de validação de mesmo usuário")
    class TestesValidacaoMesmoUsuario {

        @Test
        @DisplayName("Deve lançar exceção quando usuário logado tenta alterar outro usuário")
        void deveLancarExcecaoQuandoUsuariosDiferentes() {
            // Arrange
            Long usuarioLogadoId = 1L;
            Long outroUsuarioId = 2L;
            UsuarioBase usuarioAtualizar = criarUsuario(outroUsuarioId, "Nome", "email@test.com", "login", "Endereco");

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioLogadoId, usuarioAtualizar)
            );

            assertEquals("Não é possível realizar a alteração do registro de outros usuários", exception.getMessage());
            verifyNoInteractions(buscarUsuarioPorIdUseCase);
            verifyNoInteractions(usuarioGateway);
        }

        @Test
        @DisplayName("Deve lançar exceção quando ID do usuário a atualizar é nulo")
        void deveLancarExcecaoQuandoIdUsuarioAtualizarNulo() {
            // Arrange
            Long usuarioLogadoId = 1L;
            UsuarioBase usuarioAtualizar = criarUsuario(null, "Nome", "email@test.com", "login", "Endereco");

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioLogadoId, usuarioAtualizar)
            );

            assertEquals("Não é possível realizar a alteração do registro de outros usuários", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes de validação de dados")
    class TestesValidacaoDados {

        @Test
        @DisplayName("Deve lançar exceção quando validação de dados falha")
        void deveLancarExcecaoQuandoValidacaoDadosFalha() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "", "email@test.com", "login", "Endereco");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            doThrow(new IllegalArgumentException("Nome é obrigatório"))
                    .when(ruleDadosUsuario).validar(usuarioAtualizar);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioId, usuarioAtualizar)
            );

            assertEquals("Nome é obrigatório", exception.getMessage());
            verify(buscarUsuarioPorIdUseCase).buscarPorId(usuarioId);
            verifyNoInteractions(ruleCredenciaisUsuario);
            verify(usuarioGateway, never()).salvar(any());
        }

        @Test
        @DisplayName("Deve ignorar ValidaSePossuiSenha na validação de dados parciais")
        void deveIgnorarValidacaoSenha() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@test.com", "loginNovo", "Endereco Novo");

            ValidaSePossuiSenha validaSenha = mock(ValidaSePossuiSenha.class);
            RuleDadosUsuario outraRule = mock(RuleDadosUsuario.class);

            AtualizarDadosParciaisDadosParciaisUsuarioUseCase useCaseComSenhaRule =
                    new AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
                            buscarUsuarioPorIdUseCase,
                            usuarioGateway,
                            List.of(validaSenha, outraRule),
                            List.of(ruleCredenciaisUsuario)
                    );

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            useCaseComSenhaRule.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            verify(validaSenha, never()).validar(any());
            verify(outraRule).validar(usuarioAtualizar);
        }
    }

    @Nested
    @DisplayName("Testes de validação de credenciais")
    class TestesValidacaoCredenciais {

        @Test
        @DisplayName("Deve lançar exceção quando email já existe para outro usuário")
        void deveLancarExcecaoQuandoEmailJaExiste() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "antigo@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome", "existente@test.com", "login", "Endereco");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            doThrow(new IllegalArgumentException("Email já cadastrado"))
                    .when(ruleCredenciaisUsuario).validar(usuarioAtual, usuarioAtualizar);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioId, usuarioAtualizar)
            );

            assertEquals("Email já cadastrado", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }

        @Test
        @DisplayName("Deve lançar exceção quando login já existe para outro usuário")
        void deveLancarExcecaoQuandoLoginJaExiste() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "loginAntigo", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome", "email@test.com", "loginExistente", "Endereco");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            doThrow(new IllegalArgumentException("Login já cadastrado"))
                    .when(ruleCredenciaisUsuario).validar(usuarioAtual, usuarioAtualizar);

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> useCase.atualizar(usuarioId, usuarioAtualizar)
            );

            assertEquals("Login já cadastrado", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Testes de busca de usuário")
    class TestesBuscaUsuario {

        @Test
        @DisplayName("Deve lançar exceção quando usuário não é encontrado")
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId))
                    .thenThrow(new RuntimeException("Usuário não encontrado"));

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> useCase.atualizar(usuarioId, usuarioAtualizar)
            );

            assertEquals("Usuário não encontrado", exception.getMessage());
            verify(usuarioGateway, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("Testes de erro no gateway")
    class TestesErroGateway {

        @Test
        @DisplayName("Deve propagar exceção quando falha ao salvar no gateway")
        void deveLancarExcecaoQuandoFalhaAoSalvar() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@test.com", "loginNovo", "Endereco Novo");

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class)))
                    .thenThrow(new RuntimeException("Erro ao salvar no banco de dados"));

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> useCase.atualizar(usuarioId, usuarioAtualizar)
            );

            assertEquals("Erro ao salvar no banco de dados", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Testes com múltiplas regras")
    class TestesMultiplasRegras {

        @Test
        @DisplayName("Deve executar todas as regras de validação de dados")
        void deveExecutarTodasAsRegrasDeDados() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@test.com", "loginNovo", "Endereco Novo");

            RuleDadosUsuario rule1 = mock(RuleDadosUsuario.class);
            RuleDadosUsuario rule2 = mock(RuleDadosUsuario.class);
            RuleDadosUsuario rule3 = mock(RuleDadosUsuario.class);

            AtualizarDadosParciaisDadosParciaisUsuarioUseCase useCaseMultiplasRegras =
                    new AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
                            buscarUsuarioPorIdUseCase,
                            usuarioGateway,
                            List.of(rule1, rule2, rule3),
                            List.of(ruleCredenciaisUsuario)
                    );

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            useCaseMultiplasRegras.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            verify(rule1).validar(usuarioAtualizar);
            verify(rule2).validar(usuarioAtualizar);
            verify(rule3).validar(usuarioAtualizar);
        }

        @Test
        @DisplayName("Deve executar todas as regras de validação de credenciais")
        void deveExecutarTodasAsRegrasDeCredenciais() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@test.com", "loginNovo", "Endereco Novo");

            RuleCredenciaisUsuario ruleCredencial1 = mock(RuleCredenciaisUsuario.class);
            RuleCredenciaisUsuario ruleCredencial2 = mock(RuleCredenciaisUsuario.class);

            AtualizarDadosParciaisDadosParciaisUsuarioUseCase useCaseMultiplasRegras =
                    new AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
                            buscarUsuarioPorIdUseCase,
                            usuarioGateway,
                            List.of(ruleDadosUsuario),
                            List.of(ruleCredencial1, ruleCredencial2)
                    );

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            useCaseMultiplasRegras.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            verify(ruleCredencial1).validar(usuarioAtual, usuarioAtualizar);
            verify(ruleCredencial2).validar(usuarioAtual, usuarioAtualizar);
        }
    }

    @Nested
    @DisplayName("Testes com listas vazias de regras")
    class TestesListasVazias {

        @Test
        @DisplayName("Deve funcionar sem regras de validação de dados")
        void deveFuncionarSemRegrasDeDados() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@test.com", "loginNovo", "Endereco Novo");

            AtualizarDadosParciaisDadosParciaisUsuarioUseCase useCaseSemRegras =
                    new AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
                            buscarUsuarioPorIdUseCase,
                            usuarioGateway,
                            Collections.emptyList(),
                            List.of(ruleCredenciaisUsuario)
                    );

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCaseSemRegras.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            assertEquals(usuarioId, resultado);
            verify(usuarioGateway).salvar(usuarioAtual);
        }

        @Test
        @DisplayName("Deve funcionar sem regras de validação de credenciais")
        void deveFuncionarSemRegrasDeCredenciais() {
            // Arrange
            Long usuarioId = 1L;
            UsuarioBase usuarioAtual = criarUsuario(usuarioId, "Nome", "email@test.com", "login", "Endereco");
            UsuarioBase usuarioAtualizar = criarUsuario(usuarioId, "Nome Novo", "novo@test.com", "loginNovo", "Endereco Novo");

            AtualizarDadosParciaisDadosParciaisUsuarioUseCase useCaseSemRegras =
                    new AtualizarDadosParciaisDadosParciaisUsuarioUseCase(
                            buscarUsuarioPorIdUseCase,
                            usuarioGateway,
                            List.of(ruleDadosUsuario),
                            Collections.emptyList()
                    );

            when(buscarUsuarioPorIdUseCase.buscarPorId(usuarioId)).thenReturn(usuarioAtual);
            when(usuarioGateway.salvar(any(UsuarioBase.class))).thenReturn(usuarioId);

            // Act
            Long resultado = useCaseSemRegras.atualizar(usuarioId, usuarioAtualizar);

            // Assert
            assertEquals(usuarioId, resultado);
            verify(usuarioGateway).salvar(usuarioAtual);
        }
    }
}